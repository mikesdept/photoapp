package mikes.dept.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import mikes.dept.data.PhotoRepositoryImpl
import mikes.dept.data.database.PhotoAppDatabase
import mikes.dept.data.database.entities.PhotoDBEntity
import mikes.dept.data.database.entities.PhotoRemoteKeysDBEntity
import mikes.dept.data.mapper.toDb
import mikes.dept.data.mapper.toDomain
import mikes.dept.domain.entities.PhotoEntity
import mikes.dept.domain.exceptions.FirstPageNetworkException

@OptIn(ExperimentalPagingApi::class)
class PhotoPagingRemoteMediatorDataSource(
    private val photoAppDatabase: PhotoAppDatabase,
    private val networkDataSource: PhotoNetworkDataSource,
    private val photoFilesDataSource: PhotoFilesDataSource
) : RemoteMediator<Int, PhotoDBEntity>() {

    private companion object {
        private const val DEFAULT_START_PAGE = 0
        private const val ONE_PAGE = 1
        private const val MIN_REMOTE_PHOTOS_COUNT = 1
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PhotoDBEntity>
    ): MediatorResult {
        val currentPageData = getCurrentPageDataByLoadTypeAndState(loadType = loadType, state = state)
        val currentPage = when {
            currentPageData.currentPage != null -> currentPageData.currentPage
            currentPageData.endOfPaginationReached -> return MediatorResult.Success(endOfPaginationReached = true)
            else -> return MediatorResult.Success(endOfPaginationReached = false)
        }

        val result = runCatching {
            val filePhotosAtCurrentPage = photoFilesDataSource.getPhotoFiles(page = currentPage)

            val remainingRemotePhotosCount = PhotoRepositoryImpl.PAGE_SIZE - filePhotosAtCurrentPage.size
            val remotePhotos = when {
                remainingRemotePhotosCount >= MIN_REMOTE_PHOTOS_COUNT -> networkDataSource.getPhotos(page = currentPage)
                    .take(remainingRemotePhotosCount)
                    .map { photoResponse -> photoResponse.toDomain(page = currentPage) }
                else -> listOf()
            }

            val photos = filePhotosAtCurrentPage + remotePhotos
            Result.Success(data = photos)
        }.getOrElse { throwable ->
            when {
                currentPage == DEFAULT_START_PAGE -> Result.Failure(throwable = FirstPageNetworkException())
                else -> Result.Failure(throwable = throwable)
            }
        }

        return when (result) {
            is Result.Failure -> MediatorResult.Error(result.throwable)
            is Result.Success -> {
                val endOfPaginationReached = result.data.isEmpty()
                photoAppDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        photoAppDatabase.photoRemoteKeysDao().clear()
                        photoAppDatabase.photoDao().clear()
                    }
                    val previousKey = if (currentPage == DEFAULT_START_PAGE) null else currentPage - ONE_PAGE
                    val nextKey = if (endOfPaginationReached) null else currentPage + ONE_PAGE
                    val keys = result.data.map { photoEntity ->
                        PhotoRemoteKeysDBEntity(id = photoEntity.id, previousKey = previousKey, nextKey = nextKey)
                    }
                    photoAppDatabase.photoRemoteKeysDao().insertAll(keys)
                    photoAppDatabase.photoDao().insertAll(result.data.toDb())
                }
                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            }
        }
    }

    private suspend fun getCurrentPageDataByLoadTypeAndState(
        loadType: LoadType,
        state: PagingState<Int, PhotoDBEntity>
    ): CurrentPageData = when (loadType) {
        LoadType.REFRESH -> CurrentPageData(
            currentPage = getRemoteKeyClosestToCurrentPosition(state)?.nextKey?.minus(ONE_PAGE) ?: DEFAULT_START_PAGE,
            endOfPaginationReached = false
        )
        LoadType.PREPEND -> CurrentPageData(
            currentPage = null,
            endOfPaginationReached = true
        )
        LoadType.APPEND -> {
            val remoteKeys = getRemoteKeyForLastItem(state = state)
            val nextKey = remoteKeys?.nextKey
            CurrentPageData(
                currentPage = nextKey,
                endOfPaginationReached = remoteKeys == null
            )
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PhotoDBEntity>
    ): PhotoRemoteKeysDBEntity? = state.anchorPosition?.let { position ->
        state.closestItemToPosition(position)?.id?.let { id ->
            photoAppDatabase.photoRemoteKeysDao().getRemoteKeyById(id = id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, PhotoDBEntity>
    ): PhotoRemoteKeysDBEntity? {
        val lastPagingItemId = state.pages.lastOrNull()?.data?.lastOrNull()?.id
        val remoteKeyId = when (lastPagingItemId) {
            null -> photoAppDatabase.photoDao().getLastPhoto()?.id
            else -> lastPagingItemId
        }
        return remoteKeyId?.let { id -> photoAppDatabase.photoRemoteKeysDao().getRemoteKeyById(id = id) }
    }

    private fun List<PhotoEntity>.toDb(): List<PhotoDBEntity> = map { photoEntity ->
        photoEntity.toDb()
    }

    private sealed class Result {

        data class Success(val data: List<PhotoEntity>): Result()

        data class Failure(val throwable: Throwable): Result()

    }

    private data class CurrentPageData(
        val currentPage: Int?,
        val endOfPaginationReached: Boolean
    )

}

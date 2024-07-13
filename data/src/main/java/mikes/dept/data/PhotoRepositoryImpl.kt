package mikes.dept.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mikes.dept.data.database.PhotoAppDatabase
import mikes.dept.data.datasource.PhotoFilesDataSource
import mikes.dept.data.datasource.PhotoNetworkDataSource
import mikes.dept.data.datasource.PhotoPagingCacheDataSource
import mikes.dept.data.datasource.PhotoPagingRemoteMediatorDataSource
import mikes.dept.data.mapper.toDomain
import mikes.dept.domain.entities.PhotoEntity
import mikes.dept.domain.repository.PhotoRepository
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val photoAppDatabase: PhotoAppDatabase,
    private val networkDataSource: PhotoNetworkDataSource,
    private val photoFilesDataSource: PhotoFilesDataSource
) : PhotoRepository<PagingData<PhotoEntity>> {

    companion object {
        const val PAGE_SIZE = 10
        private val pagingConfig = PagingConfig(pageSize = PAGE_SIZE)
    }

    override fun getLocalCachePhotos(): Flow<PagingData<PhotoEntity>> = Pager(
        config = pagingConfig,
        pagingSourceFactory = { PhotoPagingCacheDataSource(photoAppDatabase = photoAppDatabase) }
    ).flow

    @OptIn(ExperimentalPagingApi::class)
    override fun getFilesAndRemotePhotos(): Flow<PagingData<PhotoEntity>> = Pager(
        config = pagingConfig,
        remoteMediator = PhotoPagingRemoteMediatorDataSource(
            photoAppDatabase = photoAppDatabase,
            networkDataSource = networkDataSource,
            photoFilesDataSource = photoFilesDataSource
        ),
        pagingSourceFactory = { photoAppDatabase.photoDao().getAll() }
    ).flow.map { pagingData ->
        pagingData.map { photoDbEntity -> photoDbEntity.toDomain() }
    }

    override suspend fun savePhotoFile(base64: String) {
        photoFilesDataSource.savePhotoFile(base64 = base64)
    }

}

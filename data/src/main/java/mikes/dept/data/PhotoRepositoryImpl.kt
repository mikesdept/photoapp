package mikes.dept.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mikes.dept.data.database.PhotoAppDatabase
import mikes.dept.data.datasource.PhotoNetworkDataSource
import mikes.dept.data.datasource.PhotoPagingDataSource
import mikes.dept.data.datasource.PhotoPagingRemoteMediatorDataSource
import mikes.dept.domain.entities.PhotoEntity
import mikes.dept.domain.repository.PhotoRepository
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val photoAppDatabase: PhotoAppDatabase,
    private val networkDataSource: PhotoNetworkDataSource
) : PhotoRepository<PagingData<PhotoEntity>> {

    private companion object {
        private const val PAGE_SIZE = 10
        private val pagingConfig = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false, initialLoadSize = PAGE_SIZE)
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getLocalCachePhotos(): Flow<PagingData<PhotoEntity>> = Pager(
        config = pagingConfig,
        remoteMediator = PhotoPagingRemoteMediatorDataSource(
            photoAppDatabase = photoAppDatabase,
            networkDataSource = networkDataSource
        ),
        pagingSourceFactory = { photoAppDatabase.photoDao().getAll() }
    ).flow.map { pagingData ->
        pagingData.map { photoDbEntity -> photoDbEntity.toDomain() }
    }

    override fun getRemotePhotos(): Flow<PagingData<PhotoEntity>> = Pager(
        config = pagingConfig,
        pagingSourceFactory = { PhotoPagingDataSource(networkDataSource = networkDataSource) }
    ).flow

}

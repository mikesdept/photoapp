package mikes.dept.data

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mikes.dept.data.database.PhotoAppDatabase
import mikes.dept.data.datasource.PhotoNetworkDataSource
import mikes.dept.data.datasource.PhotoPagingCacheDataSource
import mikes.dept.data.datasource.PhotoPagingFilesDataSource
import mikes.dept.data.datasource.PhotoPagingRemoteMediatorDataSource
import mikes.dept.data.mapper.toDomain
import mikes.dept.domain.entities.PhotoEntity
import mikes.dept.domain.repository.FilesRepository
import mikes.dept.domain.repository.PhotoRepository
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val context: Context,
    private val photoAppDatabase: PhotoAppDatabase,
    private val networkDataSource: PhotoNetworkDataSource,
    private val filesRepository: FilesRepository
) : PhotoRepository<PagingData<PhotoEntity>> {

    private companion object {
        private const val PAGE_SIZE = 10
        private val pagingConfig = PagingConfig(pageSize = PAGE_SIZE)
    }

    override fun getLocalCachePhotos(): Flow<PagingData<PhotoEntity>> = Pager(
        config = pagingConfig,
        pagingSourceFactory = { PhotoPagingCacheDataSource(photoAppDatabase = photoAppDatabase) }
    ).flow

    @OptIn(ExperimentalPagingApi::class)
    override fun getRemotePhotos(): Flow<PagingData<PhotoEntity>> = Pager(
        config = pagingConfig,
        remoteMediator = PhotoPagingRemoteMediatorDataSource(
            photoAppDatabase = photoAppDatabase,
            networkDataSource = networkDataSource
        ),
        pagingSourceFactory = { photoAppDatabase.photoDao().getAll() }
    ).flow.map { pagingData ->
        pagingData.map { photoDbEntity -> photoDbEntity.toDomain() }
    }

    override fun getPhotoFiles(): Flow<PagingData<PhotoEntity>> = Pager(
        config = pagingConfig,
        pagingSourceFactory = { PhotoPagingFilesDataSource(filesRepository = filesRepository) }
    ).flow

    override suspend fun savePhotoFile(base64: String) {
        val file = filesRepository.createFile()
        filesRepository.writeToFile(file = file, content = base64)
    }

}

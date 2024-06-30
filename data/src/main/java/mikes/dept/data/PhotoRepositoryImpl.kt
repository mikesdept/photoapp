package mikes.dept.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import mikes.dept.data.datasource.PhotoNetworkDataSource
import mikes.dept.data.datasource.PhotoPagingDataSource
import mikes.dept.domain.entities.PhotoEntity
import mikes.dept.domain.repository.PhotoRepository
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val networkDataSource: PhotoNetworkDataSource
) : PhotoRepository<PagingData<PhotoEntity>> {

    private companion object {
        private const val PAGE_SIZE = 50
        private val pagingConfig = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false, initialLoadSize = PAGE_SIZE)
    }

    override fun getPhotos(): Flow<PagingData<PhotoEntity>> = Pager(
        config = pagingConfig,
        pagingSourceFactory = { PhotoPagingDataSource(networkDataSource) }
    ).flow

}

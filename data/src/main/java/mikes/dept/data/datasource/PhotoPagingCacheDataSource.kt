package mikes.dept.data.datasource

import mikes.dept.data.database.PhotoAppDatabase
import mikes.dept.domain.entities.PhotoEntity

class PhotoPagingCacheDataSource(
    private val photoAppDatabase: PhotoAppDatabase
) : PhotoPagingSingleDataSource() {

    override suspend fun getPhotos(currentPage: Int): List<PhotoEntity> = photoAppDatabase
        .photoDao()
        .getPhotos(page = currentPage)
        ?.map { photoDBEntity -> photoDBEntity.toDomain() }
        ?: listOf()

}

package mikes.dept.data.datasource

import mikes.dept.data.mapper.toDomain
import mikes.dept.domain.entities.PhotoEntity

class PhotoPagingNetworkDataSource(
    private val networkDataSource: PhotoNetworkDataSource
) : PhotoPagingSingleDataSource() {

    override suspend fun getPhotos(currentPage: Int): List<PhotoEntity> = networkDataSource
        .getPhotos(page = currentPage)
        .map { photoResponse -> photoResponse.toDomain(page = currentPage) }

}

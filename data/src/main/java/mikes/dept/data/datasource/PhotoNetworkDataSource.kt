package mikes.dept.data.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mikes.dept.data.network.NetworkService
import mikes.dept.domain.entities.AppKeys
import mikes.dept.domain.entities.PhotoEntity
import javax.inject.Inject

interface PhotoNetworkDataSource {

    suspend fun getPhotos(page: Int): List<PhotoEntity>

}

class PhotoNetworkDataSourceImpl @Inject constructor(
    private val networkService: NetworkService,
    private val appKeys: AppKeys
) : PhotoNetworkDataSource {

    override suspend fun getPhotos(page: Int): List<PhotoEntity> = withContext(Dispatchers.IO) {
        networkService.getPhotos(clientId = appKeys.accessKey, page = page)
            .map { photoResponse -> photoResponse.toDomain() }
    }

}

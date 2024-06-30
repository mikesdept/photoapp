package mikes.dept.data.datasource

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import mikes.dept.data.network.NetworkService
import mikes.dept.data.network.entities.response.PhotoResponse
import mikes.dept.data.utils.JsonUtils
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

class PhotoNetworkMockDataSourceImpl @Inject constructor(
    private val context: Context,
    private val json: Json
) : PhotoNetworkDataSource {

    private companion object {
        private const val MOCK_RESPONSE_DELAY = 2000L
    }

    override suspend fun getPhotos(page: Int): List<PhotoEntity> = withContext(Dispatchers.IO) {
        runCatching {
            delay(MOCK_RESPONSE_DELAY)
            val jsonString = JsonUtils.loadJSONFromAsset(context = context, fileName = "mock_response.json")
            json.decodeFromString(
                deserializer = ListSerializer(elementSerializer = PhotoResponse.serializer()),
                string = jsonString
            ).map { photoResponse -> photoResponse.toDomain() }
        }.getOrElse { listOf() }
    }

}

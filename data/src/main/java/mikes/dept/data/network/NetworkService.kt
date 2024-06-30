package mikes.dept.data.network

import mikes.dept.data.network.entities.response.PhotoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET(ApiEndpoint.getPhotos)
    suspend fun getPhotos(
        @Query("client_id") clientId: String,
        @Query("page") page: Int
    ): List<PhotoResponse>

}

package mikes.dept.data.network.entities.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoResponse(
    @SerialName("id") val id: String,
    @SerialName("urls") val urls: Urls
) {

    @Serializable
    data class Urls(
        @SerialName("regular") val regular: String,
        @SerialName("small") val small: String
    )

}

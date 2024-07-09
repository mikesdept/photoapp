package mikes.dept.data.network.entities.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import mikes.dept.data.database.entities.PhotoDBEntity
import mikes.dept.domain.entities.PhotoEntity

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

    fun toDb(page: Int): PhotoDBEntity = PhotoDBEntity(
        id = id,
        page = page,
        regularUrl = urls.regular,
        smallUrl = urls.small
    )

    fun toDomain(): PhotoEntity = PhotoEntity(
        id = id,
        regularUrl = urls.regular,
        smallUrl = urls.small
    )

}

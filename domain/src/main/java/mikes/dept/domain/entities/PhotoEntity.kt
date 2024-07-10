package mikes.dept.domain.entities

data class PhotoEntity(
    val id: String,
    val page: Int,
    val photoContentEntity: PhotoContentEntity
)

package mikes.dept.data.mapper

import mikes.dept.data.database.entities.PhotoContentDBEntity
import mikes.dept.data.database.entities.PhotoDBEntity
import mikes.dept.domain.entities.PhotoContentEntity
import mikes.dept.domain.entities.PhotoEntity

fun PhotoDBEntity.toDomain(): PhotoEntity = PhotoEntity(
    id = id,
    page = page,
    photoContentEntity = photoContentDBEntity.toDomain()
)

private fun PhotoContentDBEntity.toDomain(): PhotoContentEntity = when (this) {
    is PhotoContentDBEntity.Url -> PhotoContentEntity.Url(smallUrl = smallUrl, regularUrl = regularUrl)
    is PhotoContentDBEntity.Base64 -> PhotoContentEntity.Base64(base64 = base64)
}

package mikes.dept.data.mapper

import mikes.dept.data.database.entities.PhotoContentDBEntity
import mikes.dept.data.database.entities.PhotoDBEntity
import mikes.dept.domain.entities.PhotoContentEntity
import mikes.dept.domain.entities.PhotoEntity

fun PhotoEntity.toDb(): PhotoDBEntity = PhotoDBEntity(
    id = id,
    page = page,
    photoContentDBEntity = photoContentEntity.toDb()
)

private fun PhotoContentEntity.toDb(): PhotoContentDBEntity = when (this) {
    is PhotoContentEntity.Url -> PhotoContentDBEntity.Url(smallUrl = smallUrl, regularUrl = regularUrl)
    is PhotoContentEntity.Base64 -> PhotoContentDBEntity.Base64(base64 = base64)
}

package mikes.dept.data.mapper

import mikes.dept.data.database.entities.PhotoContentDBEntity
import mikes.dept.data.database.entities.PhotoDBEntity
import mikes.dept.data.network.entities.response.PhotoResponse
import mikes.dept.domain.entities.PhotoContentEntity
import mikes.dept.domain.entities.PhotoEntity

fun PhotoResponse.toDomain(page: Int): PhotoEntity = PhotoEntity(
    id = id,
    page = page,
    photoContentEntity = PhotoContentEntity.Url(
        smallUrl = urls.small,
        regularUrl = urls.regular
    )
)

fun PhotoResponse.toDb(page: Int): PhotoDBEntity = PhotoDBEntity(
    id = id,
    page = page,
    photoContentDBEntity = PhotoContentDBEntity.Url(
        smallUrl = urls.small,
        regularUrl = urls.regular
    )
)

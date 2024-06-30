package mikes.dept.domain.repository

import kotlinx.coroutines.flow.Flow

interface PhotoRepository<T> {

    fun getPhotos(): Flow<T>

}

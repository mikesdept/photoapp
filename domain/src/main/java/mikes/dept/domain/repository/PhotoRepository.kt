package mikes.dept.domain.repository

import kotlinx.coroutines.flow.Flow

interface PhotoRepository<T> {

    fun getLocalCachePhotos(): Flow<T>

    fun getFilesAndRemotePhotos(): Flow<T>

    suspend fun savePhotoFile(base64: String)

}

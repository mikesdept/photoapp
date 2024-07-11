package mikes.dept.data.datasource

import android.content.Context
import mikes.dept.domain.entities.PhotoContentEntity
import mikes.dept.domain.entities.PhotoEntity
import mikes.dept.domain.repository.FilesRepository

class PhotoPagingFilesDataSource(
    private val context: Context,
    private val filesRepository: FilesRepository
) : PhotoPagingSingleDataSource() {

    override suspend fun getPhotos(currentPage: Int): List<PhotoEntity> = when (currentPage) {
        0 -> context.filesDir.listFiles()
            ?.filterNotNull()
            ?.mapNotNull { file ->
                val content = filesRepository.readFromFile(file = file)
                when {
                    content.isEmpty() || content.isBlank() -> null
                    else -> PhotoEntity(
                        id = file.name,
                        page = 0,
                        photoContentEntity = PhotoContentEntity.Base64(base64 = content)
                    )
                }
            }
            ?: listOf()
        else -> listOf()
    }

}

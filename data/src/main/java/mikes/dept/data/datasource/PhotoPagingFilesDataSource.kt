package mikes.dept.data.datasource

import mikes.dept.domain.entities.PhotoContentEntity
import mikes.dept.domain.entities.PhotoEntity
import mikes.dept.domain.repository.FilesRepository

class PhotoPagingFilesDataSource(
    private val filesRepository: FilesRepository
) : PhotoPagingSingleDataSource() {

    override suspend fun getPhotos(currentPage: Int): List<PhotoEntity> = when (currentPage) {
        0 -> filesRepository.getFileList()
            .mapNotNull { file ->
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
        else -> listOf()
    }

}

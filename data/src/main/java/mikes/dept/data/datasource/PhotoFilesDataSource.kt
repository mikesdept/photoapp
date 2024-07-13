package mikes.dept.data.datasource

import mikes.dept.data.PhotoRepositoryImpl
import mikes.dept.domain.entities.PhotoContentEntity
import mikes.dept.domain.entities.PhotoEntity
import mikes.dept.domain.repository.FilesRepository
import javax.inject.Inject

interface PhotoFilesDataSource {

    suspend fun getPhotoFiles(page: Int): List<PhotoEntity>

    suspend fun savePhotoFile(base64: String)

}

class PhotoFilesDataSourceImpl @Inject constructor(
    private val filesRepository: FilesRepository
) : PhotoFilesDataSource {

    private companion object {
        private const val ONE = 1
    }

    override suspend fun getPhotoFiles(page: Int): List<PhotoEntity> {
        val filePhotosMinIndex = page * PhotoRepositoryImpl.PAGE_SIZE
        val filePhotosMaxIndex = (page + ONE) * PhotoRepositoryImpl.PAGE_SIZE - ONE
        return filesRepository.getFileList()
            .mapNotNull { file ->
                val content = filesRepository.readFromFile(file = file)
                when {
                    content.isEmpty() || content.isBlank() -> null
                    else -> PhotoEntity(
                        id = file.name,
                        page = page,
                        photoContentEntity = PhotoContentEntity.Base64(base64 = content)
                    )
                }
            }
            .filterIndexed { index, _ -> index in filePhotosMinIndex..filePhotosMaxIndex }
    }

    override suspend fun savePhotoFile(base64: String) {
        val file = filesRepository.createFile()
        filesRepository.writeToFile(file = file, content = base64)
    }

}

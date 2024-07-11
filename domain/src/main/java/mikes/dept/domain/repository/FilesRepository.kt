package mikes.dept.domain.repository

import java.io.File

interface FilesRepository {

    suspend fun createFile(): File

    suspend fun getFileList(): List<File>

    suspend fun writeToFile(file: File, content: String)

    suspend fun readFromFile(file: File): String

}

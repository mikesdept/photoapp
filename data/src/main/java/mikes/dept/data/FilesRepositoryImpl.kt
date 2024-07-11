package mikes.dept.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mikes.dept.domain.repository.FilesRepository
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class FilesRepositoryImpl @Inject constructor(
    private val context: Context
) : FilesRepository {

    private companion object {
        private const val TXT_EXTENSION = ".txt"
        private const val DATE_TIME_PATTERN = "yyyy-MM-dd_HH:mm:ss:z"
        private const val GENERATED_PHOTOS_DIRECTORY = "generated_photos/"
    }

    override suspend fun createFile(): File = withContext(Dispatchers.IO) {
        val filesDir = context.filesDir
        if (!filesDir.exists()) {
            if (!filesDir.mkdirs()) {
                throw Exception("exception mk dirs")
            }
        }
        File(filesDir, generateFileName()).also { file ->
            if (!file.exists()) {
                file.parentFile?.mkdirs()
            }
        }
    }

    override suspend fun getFileList(): List<File> = withContext(Dispatchers.IO) {
        File(context.filesDir, GENERATED_PHOTOS_DIRECTORY).listFiles()?.filterNotNull() ?: listOf()
    }

    override suspend fun writeToFile(file: File, content: String) = withContext(Dispatchers.IO) {
        FileOutputStream(file, true).apply {
            write(content.toByteArray())
            flush()
            close()
        }
        Unit
    }

    override suspend fun readFromFile(file: File): String = withContext(Dispatchers.IO) {
        var content = ""
        try {
            val fileInputStream = FileInputStream(file)
            val bufferedReader = BufferedReader(InputStreamReader(fileInputStream))
            var tempContent: String
            while ((bufferedReader.readLine().also { tempContent = it }) != null) {
                content += tempContent
            }
            bufferedReader.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        content
    }

    private fun generateFileName(): String {
        val sdf = SimpleDateFormat(DATE_TIME_PATTERN, Locale.getDefault())
        return "$GENERATED_PHOTOS_DIRECTORY${sdf.format(Date())}$TXT_EXTENSION"
    }

}

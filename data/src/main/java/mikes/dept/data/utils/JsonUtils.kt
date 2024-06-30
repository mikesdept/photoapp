package mikes.dept.data.utils

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

object JsonUtils {

    fun loadJSONFromAsset(context: Context, fileName: String): String  {
        val stringBuilder = StringBuilder()
        runCatching {
            val inputStream = context.assets.open(fileName)
            val reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
            var line: String?
            while ((reader.readLine().also { line = it }) != null) {
                stringBuilder.append(line)
            }
            reader.close()
            inputStream.close()
        }
        return stringBuilder.toString()
    }

}

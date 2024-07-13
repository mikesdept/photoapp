package mikes.dept.presentation.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

object BitmapUtils {

    suspend fun bitmapToBase64(bitmap: Bitmap): String = withContext(Dispatchers.IO) {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    // TODO: should be suspended
    fun base64ToBitmap(base64: String): Bitmap {
        val byteArray = Base64.decode(base64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

}

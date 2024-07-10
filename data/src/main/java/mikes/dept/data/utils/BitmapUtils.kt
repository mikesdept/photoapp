package mikes.dept.data.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.nio.ByteBuffer

object BitmapUtils {

    fun bitmapToBase64(bitmap: Bitmap): String? = runCatching {
        val byteBuffer = ByteBuffer.allocate(bitmap.height * bitmap.rowBytes)
        bitmap.copyPixelsToBuffer(byteBuffer)
        val byteArray = byteBuffer.array()
        Base64.encodeToString(byteArray, Base64.DEFAULT)
    }.getOrNull()

    fun base64ToBitmap(base64: String): Bitmap? = runCatching {
        val byteArray = Base64.decode(base64, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }.getOrNull()

}

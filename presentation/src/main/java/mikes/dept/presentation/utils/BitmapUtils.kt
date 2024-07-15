package mikes.dept.presentation.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

object BitmapUtils {

    private var cachedBitmap: Bitmap? = null

    suspend fun bitmapToBase64(bitmap: Bitmap): String = withContext(Dispatchers.IO) {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    // TODO: should be suspended
    fun base64ToBitmap(base64: String, inBitmapReuse: Boolean): Bitmap {
        val byteArray = Base64.decode(base64, Base64.DEFAULT)
        val bitmap = when {
            inBitmapReuse -> {
                val bitmapOptions = BitmapFactory.Options().apply {
                    when (cachedBitmap) {
                        null -> inMutable = true
                        else -> inBitmap = cachedBitmap
                    }
                }
                BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size, bitmapOptions)
                    .also { bitmap -> cachedBitmap = bitmap }
            }
            else -> BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }
        return bitmap
    }

}

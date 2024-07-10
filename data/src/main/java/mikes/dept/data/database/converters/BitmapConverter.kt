package mikes.dept.data.database.converters

import androidx.room.TypeConverter
import mikes.dept.data.database.entities.PhotoContentDBEntity

class BitmapConverter {

    private companion object {
        private const val BASE_64_PREFIX = "base64"
        private const val URL_PREFIX = "url"
        private const val SEPARATOR = "***"
    }

    @TypeConverter
    fun photoContentDBEntityToString(photoContentDBEntity: PhotoContentDBEntity?): String? = when (photoContentDBEntity) {
        is PhotoContentDBEntity.Url -> "$URL_PREFIX$SEPARATOR${photoContentDBEntity.smallUrl}$SEPARATOR${photoContentDBEntity.regularUrl}"
        is PhotoContentDBEntity.Base64 -> "$BASE_64_PREFIX$SEPARATOR${photoContentDBEntity.base64}"
        else -> null
    }

    @TypeConverter
    fun stringToPhotoContentDBEntity(json: String): PhotoContentDBEntity? = runCatching {
        val content = json.split(SEPARATOR)
        return when {
            content[0] == URL_PREFIX -> PhotoContentDBEntity.Url(
                smallUrl = content[1],
                regularUrl = content[2]
            )
            content[0] == BASE_64_PREFIX -> PhotoContentDBEntity.Base64(
                base64 = content[1]
            )
            else -> null
        }
    }.getOrNull()

}

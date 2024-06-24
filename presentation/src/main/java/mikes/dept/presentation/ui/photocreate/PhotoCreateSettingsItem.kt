package mikes.dept.presentation.ui.photocreate

import android.net.Uri
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class PhotoCreateSettingsItem(
    val textSettings: TextSettings,
    val backgroundColor: Color,
    val image: Uri?,
) {

    companion object {
        fun defaultSettings(): PhotoCreateSettingsItem = PhotoCreateSettingsItem(
            textSettings = TextSettings.defaultSettings(),
            backgroundColor = Color.White,
            image = null
        )
    }

    data class TextSettings(
        val text: String,
        val textSize: TextUnit,
        val textColor: Color,
        val offsetX: Float,
        val offsetY: Float,
    ) {

        companion object {
            fun defaultSettings(): TextSettings = TextSettings(
                text = "",
                textSize = 50.sp,
                textColor = Color.Black,
                offsetX = 0f,
                offsetY = 0f,
            )
        }

    }

}

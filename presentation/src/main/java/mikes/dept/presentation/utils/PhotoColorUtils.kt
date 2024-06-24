package mikes.dept.presentation.utils

import androidx.compose.ui.graphics.Color

object PhotoColorUtils {

    private val colors = listOf(Color.White, Color.Black, Color.Red, Color.Yellow, Color.Green, Color.Blue)

    fun getInitialBackgroundColor(): Color = colors[0]

    fun getInitialTextColor(): Color = colors[1]

    fun getNextColor(currentColor: Color): Color {
        val nextIndex = colors
            .mapIndexed { index, color -> index to color }
            .firstOrNull { (_, color) -> color.value == currentColor.value }
            ?.first
            ?.takeIf { index -> index < colors.size - 1 }
            ?.let { index -> index + 1 }
            ?: 0
        return colors[nextIndex]
    }

}

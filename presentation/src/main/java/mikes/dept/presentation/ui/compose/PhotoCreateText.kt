package mikes.dept.presentation.ui.compose

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import mikes.dept.presentation.ui.photocreate.PhotoCreateSettingsItem
import kotlin.math.roundToInt

@Composable
fun PhotoCreateText(
    textSettings: State<PhotoCreateSettingsItem.TextSettings>,
    onTextOffsetChanged: (Offset) -> Unit,
    onTextSizeChanged: (TextUnit) -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = textSettings.value.text,
        fontSize = textSettings.value.textSize,
        color = textSettings.value.textColor,
        modifier = modifier
            .offset { IntOffset(x = textSettings.value.offsetX.roundToInt(), y = textSettings.value.offsetY.roundToInt()) }
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    onTextOffsetChanged(Offset(x = textSettings.value.offsetX + pan.x, y = textSettings.value.offsetY + pan.y))
                    onTextSizeChanged(textSettings.value.textSize * zoom)
                }
            }
    )
}

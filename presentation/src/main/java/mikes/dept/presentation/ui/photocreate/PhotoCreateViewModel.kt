package mikes.dept.presentation.ui.photocreate

import android.net.Uri
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import mikes.dept.presentation.ui.core.navdirections.NavDirectionsViewModel
import mikes.dept.presentation.ui.core.navdirections.NavDirectionsViewModelImpl
import mikes.dept.presentation.ui.core.navdirections.event.ErrorEvent
import mikes.dept.presentation.ui.core.navdirections.event.NavDirectionsEvent
import javax.inject.Inject

interface PhotoCreateViewModel : NavDirectionsViewModel {

    val textSettings: Flow<PhotoCreateSettingsItem.TextSettings>
    val text: Flow<String>
    val textColor: Flow<Color>
    val backgroundColor: Flow<Color>
    val image: Flow<Uri?>

    fun onTextInputChanged(text: String)
    fun onTextOffsetChanged(offset: Offset)
    fun onTextSizeChanged(textUnit: TextUnit)
    fun onChangeBackground()
    fun onChangeTextColor()
    fun onClickImagePicker()

}

class PhotoCreateViewModelImpl @Inject constructor() : NavDirectionsViewModelImpl(), PhotoCreateViewModel {

    private val photoCreateSettingsItem: MutableStateFlow<PhotoCreateSettingsItem> = MutableStateFlow(PhotoCreateSettingsItem.defaultSettings())

    override val textSettings: Flow<PhotoCreateSettingsItem.TextSettings> = photoCreateSettingsItem
        .map { settings -> settings.textSettings }
        .distinctUntilChanged()

    override val text: Flow<String> = textSettings
        .map { settings -> settings.text }
        .distinctUntilChanged()

    override val textColor: Flow<Color> = textSettings
        .map { settings -> settings.textColor }
        .distinctUntilChanged()

    override val backgroundColor: Flow<Color> = photoCreateSettingsItem
        .map { settings -> settings.backgroundColor }
        .distinctUntilChanged()

    override val image: Flow<Uri?> = photoCreateSettingsItem
        .map { settings -> settings.image }
        .distinctUntilChanged()

    override fun onTextInputChanged(text: String) {
        photoCreateSettingsItem.value = photoCreateSettingsItem.value.copy(
            textSettings = photoCreateSettingsItem.value.textSettings.copy(
                text = text
            )
        )
    }

    override fun onTextOffsetChanged(offset: Offset) {
        photoCreateSettingsItem.value = photoCreateSettingsItem.value.copy(
            textSettings = photoCreateSettingsItem.value.textSettings.copy(
                offsetX = offset.x,
                offsetY = offset.y
            )
        )
    }

    override fun onTextSizeChanged(textUnit: TextUnit) {
        photoCreateSettingsItem.value = photoCreateSettingsItem.value.copy(
            textSettings = photoCreateSettingsItem.value.textSettings.copy(
                textSize = textUnit
            )
        )
    }

    override fun onChangeBackground() {
        // TODO("Not yet implemented")
    }

    override fun onChangeTextColor() {
        // TODO("Not yet implemented")
    }

    override fun onClickImagePicker() {
        // TODO("Not yet implemented")
    }

}

class PhotoCreateViewModelComposable : PhotoCreateViewModel {

    override val textSettings: Flow<PhotoCreateSettingsItem.TextSettings> = flowOf()
    override val text: Flow<String> = flowOf()
    override val textColor: Flow<Color> = flowOf()
    override val backgroundColor: Flow<Color> = flowOf()
    override val image: Flow<Uri?> = flowOf()

    override fun onTextInputChanged(text: String) {}
    override fun onTextOffsetChanged(offset: Offset) {}
    override fun onTextSizeChanged(textUnit: TextUnit) {}
    override fun onChangeBackground() {}
    override fun onChangeTextColor() {}
    override fun onClickImagePicker() {}

    override val navDirections: Flow<NavDirectionsEvent> = flowOf()
    override val error: Flow<ErrorEvent> = flowOf()

}

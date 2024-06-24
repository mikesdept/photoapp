package mikes.dept.presentation.ui.photocreate

import android.net.Uri
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import mikes.dept.presentation.ui.core.navdirections.NavDirectionsViewModel
import mikes.dept.presentation.ui.core.navdirections.NavDirectionsViewModelImpl
import mikes.dept.presentation.ui.core.navdirections.event.ErrorEvent
import mikes.dept.presentation.ui.core.navdirections.event.NavDirectionsEvent
import mikes.dept.presentation.utils.PhotoColorUtils
import javax.inject.Inject

interface PhotoCreateViewModel : NavDirectionsViewModel {

    val textSettings: StateFlow<PhotoCreateSettingsItem.TextSettings>
    val text: StateFlow<String>
    val textColor: StateFlow<Color>
    val backgroundColor: StateFlow<Color>
    val image: StateFlow<Uri?>
    val imageLastSelected: StateFlow<Boolean>

    fun onTextInputChanged(text: String)
    fun onTextOffsetChanged(offset: Offset)
    fun onTextSizeChanged(textUnit: TextUnit)
    fun onChangeBackground()
    fun onChangeTextColor()
    fun onImageSelected(uri: Uri)

}

class PhotoCreateViewModelImpl @Inject constructor() : NavDirectionsViewModelImpl(), PhotoCreateViewModel {

    private val photoCreateSettingsItem: MutableStateFlow<PhotoCreateSettingsItem> = MutableStateFlow(
        PhotoCreateSettingsItem.defaultSettings()
    )

    override val textSettings: StateFlow<PhotoCreateSettingsItem.TextSettings> = photoCreateSettingsItem
        .map { settings -> settings.textSettings }
        .distinctUntilChanged()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PhotoCreateSettingsItem.TextSettings.defaultSettings()
        )

    override val text: StateFlow<String> = textSettings
        .map { settings -> settings.text }
        .distinctUntilChanged()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = ""
        )

    override val textColor: StateFlow<Color> = textSettings
        .map { settings -> settings.textColor }
        .distinctUntilChanged()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PhotoColorUtils.getInitialTextColor()
        )

    override val backgroundColor: StateFlow<Color> = photoCreateSettingsItem
        .map { settings -> settings.backgroundColor }
        .distinctUntilChanged()
        .onEach { imageLastSelected.value = false }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PhotoColorUtils.getInitialBackgroundColor()
        )

    override val image: StateFlow<Uri?> = photoCreateSettingsItem
        .map { settings -> settings.image }
        .distinctUntilChanged()
        .onEach { imageLastSelected.value = true }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )

    override val imageLastSelected: MutableStateFlow<Boolean> = MutableStateFlow(false)

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
        photoCreateSettingsItem.value = photoCreateSettingsItem.value.copy(
            backgroundColor = PhotoColorUtils.getNextColor(
                currentColor = photoCreateSettingsItem.value.backgroundColor
            )
        )
    }

    override fun onChangeTextColor() {
        photoCreateSettingsItem.value = photoCreateSettingsItem.value.copy(
            textSettings = photoCreateSettingsItem.value.textSettings.copy(
                textColor = PhotoColorUtils.getNextColor(
                    currentColor = photoCreateSettingsItem.value.textSettings.textColor
                )
            )
        )
    }

    override fun onImageSelected(uri: Uri) {
        photoCreateSettingsItem.value = photoCreateSettingsItem.value.copy(
            image = uri
        )
    }

}

class PhotoCreateViewModelComposable : PhotoCreateViewModel {

    override val textSettings: StateFlow<PhotoCreateSettingsItem.TextSettings> = MutableStateFlow(
        PhotoCreateSettingsItem.TextSettings.defaultSettings()
    )
    override val text: StateFlow<String> = MutableStateFlow("")
    override val textColor: StateFlow<Color> = MutableStateFlow(PhotoColorUtils.getInitialTextColor())
    override val backgroundColor: StateFlow<Color> = MutableStateFlow(PhotoColorUtils.getInitialBackgroundColor())
    override val image: StateFlow<Uri?> = MutableStateFlow(null)
    override val imageLastSelected: StateFlow<Boolean> = MutableStateFlow(false)

    override fun onTextInputChanged(text: String) {}
    override fun onTextOffsetChanged(offset: Offset) {}
    override fun onTextSizeChanged(textUnit: TextUnit) {}
    override fun onChangeBackground() {}
    override fun onChangeTextColor() {}
    override fun onImageSelected(uri: Uri) {}

    override val navDirections: Flow<NavDirectionsEvent> = flowOf()
    override val error: Flow<ErrorEvent> = flowOf()

}

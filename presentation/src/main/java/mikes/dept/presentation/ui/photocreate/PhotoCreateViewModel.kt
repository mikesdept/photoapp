package mikes.dept.presentation.ui.photocreate

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import mikes.dept.domain.entities.PhotoEntity
import mikes.dept.domain.repository.PhotoRepository
import mikes.dept.presentation.ui.core.navdirections.NavDirectionsViewModel
import mikes.dept.presentation.ui.core.navdirections.NavDirectionsViewModelImpl
import mikes.dept.presentation.ui.core.navdirections.event.ErrorEvent
import mikes.dept.presentation.ui.core.navdirections.event.NavDirectionsEvent
import mikes.dept.presentation.utils.BitmapUtils
import mikes.dept.presentation.utils.PhotoColorUtils
import javax.inject.Inject

interface PhotoCreateViewModel : NavDirectionsViewModel {

    val textSettings: StateFlow<PhotoCreateSettingsItem.TextSettings>
    val text: StateFlow<String>
    val textColor: StateFlow<Color>
    val backgroundColor: StateFlow<Color>
    val image: StateFlow<Uri?>
    val imageLastSelected: StateFlow<Boolean>
    val contentSize: StateFlow<Int?>

    fun onTextInputChanged(text: String)
    fun onTextOffsetChanged(offset: Offset)
    fun onTextSizeChanged(textUnit: TextUnit)
    fun onChangeBackground()
    fun onChangeTextColor()
    fun onImageSelected(uri: Uri)
    fun updateContentSize(size: Int)
    fun onClickCreatePhoto(bitmap: Bitmap)

}

class PhotoCreateViewModelImpl @Inject constructor(
    private val photoRepository: PhotoRepository<PagingData<PhotoEntity>>
) : NavDirectionsViewModelImpl(), PhotoCreateViewModel {

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

    override val contentSize: MutableStateFlow<Int?> = MutableStateFlow(null)

    override fun onTextInputChanged(text: String) {
        photoCreateSettingsItem.value = photoCreateSettingsItem.value.copy(
            textSettings = photoCreateSettingsItem.value.textSettings.copy(
                text = text
            )
        )
    }

    override fun onTextOffsetChanged(offset: Offset) {
        val contentSize = this.contentSize.value
        // TODO: condition should be improved in order to move text only inside the box
        if (contentSize != null && offset.x >= 0f && offset.y >= 0f && offset.x <= contentSize - 100 && offset.y < contentSize - 100) {
            photoCreateSettingsItem.value = photoCreateSettingsItem.value.copy(
                textSettings = photoCreateSettingsItem.value.textSettings.copy(
                    offsetX = offset.x,
                    offsetY = offset.y
                )
            )
        }
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

    override fun updateContentSize(size: Int) {
        contentSize.value = size
    }

    override fun onClickCreatePhoto(bitmap: Bitmap) {
        doSingleAction(
            action = {
                val base64 = BitmapUtils.bitmapToBase64(bitmap = bitmap)
                photoRepository.savePhotoFile(base64 = base64)
            },
            onSuccess = {}, // TODO: navigate back to list screen
            onFailure = { throwable -> showError(errorEvent = ErrorEvent.StringMessage(message = throwable.localizedMessage ?: "")) },
            onStart = {}, // TODO: show progress
            onComplete = {} // TODO: hide progress
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
    override val contentSize: StateFlow<Int?> = MutableStateFlow(null)

    override fun onTextInputChanged(text: String) {}
    override fun onTextOffsetChanged(offset: Offset) {}
    override fun onTextSizeChanged(textUnit: TextUnit) {}
    override fun onChangeBackground() {}
    override fun onChangeTextColor() {}
    override fun onImageSelected(uri: Uri) {}
    override fun updateContentSize(size: Int) {}
    override fun onClickCreatePhoto(bitmap: Bitmap) {}

    override val navDirections: Flow<NavDirectionsEvent> = flowOf()
    override val error: Flow<ErrorEvent> = flowOf()

}

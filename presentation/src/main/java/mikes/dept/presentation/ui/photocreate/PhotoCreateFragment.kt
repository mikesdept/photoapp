package mikes.dept.presentation.ui.photocreate

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mikes.dept.presentation.R
import mikes.dept.presentation.di.core.SubcomponentProvider
import mikes.dept.presentation.ui.compose.ColorWithTextButton
import mikes.dept.presentation.ui.compose.ImageWithTextButton
import mikes.dept.presentation.ui.compose.PhotoCreateText
import mikes.dept.presentation.ui.compose.PhotoCreateTextInput
import mikes.dept.presentation.ui.core.BaseComposeFragment

class PhotoCreateFragment : BaseComposeFragment<PhotoCreateViewModel>() {

    override fun initDagger(subcomponentProvider: SubcomponentProvider) = subcomponentProvider
        .providePhotoCreateSubcomponent()
        .inject(this)

    override fun initViewModel(viewModelProvider: ViewModelProvider): PhotoCreateViewModel =
        viewModelProvider[PhotoCreateViewModelImpl::class.java]

    @Composable
    override fun ComposeContent() {
        ComposeContentView(viewModel = viewModel)
    }

    override fun setup() {}
    // TODO: save to local storage
    // TODO: open existing image from list and edit at this screen
    // TODO: animation transition between screens
    @Composable
    private fun ComposeContentView(viewModel: PhotoCreateViewModel) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .weight(1f)
            ) {
                PhotoCreateText(
                    textSettings = viewModel.textSettings.collectAsStateWithLifecycle(
                        initialValue = PhotoCreateSettingsItem.TextSettings.defaultSettings()
                    ),
                    onTextOffsetChanged = { offset -> viewModel.onTextOffsetChanged(offset = offset) },
                    onTextSizeChanged = { textUnit -> viewModel.onTextSizeChanged(textUnit = textUnit) }
                )
            }
            PhotoCreateTextInput(
                value = viewModel.text.collectAsStateWithLifecycle(initialValue = ""),
                onValueChanged = { text -> viewModel.onTextInputChanged(text = text) },
                modifier = Modifier.padding(start = 8.dp, top = 16.dp, end = 8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(vertical = 16.dp)
            ) {
                ColorWithTextButton(
                    color = viewModel.backgroundColor.collectAsStateWithLifecycle(initialValue = Color.White),
                    textResId = R.string.photo_create_button_background,
                    onClick = { viewModel.onChangeBackground() },
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                ColorWithTextButton(
                    color = viewModel.textColor.collectAsStateWithLifecycle(initialValue = Color.Black),
                    textResId = R.string.photo_create_button_text_color,
                    onClick = { viewModel.onChangeTextColor() },
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                ImageWithTextButton(
                    image = viewModel.image.collectAsStateWithLifecycle(initialValue = null),
                    textResId = R.string.photo_create_button_image,
                    onClick = { viewModel.onClickImagePicker() },
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }

    @Preview
    @Composable
    private fun ComposeContentPreview() {
        ComposeContentView(viewModel = PhotoCreateViewModelComposable())
    }

}

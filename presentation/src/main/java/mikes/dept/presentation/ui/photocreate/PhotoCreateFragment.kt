package mikes.dept.presentation.ui.photocreate

import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import dev.shreyaspatil.capturable.capturable
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import kotlinx.coroutines.launch
import mikes.dept.presentation.R
import mikes.dept.presentation.di.core.SubcomponentProvider
import mikes.dept.presentation.ui.compose.ColorWithTextButton
import mikes.dept.presentation.ui.compose.ImageWithTextButton
import mikes.dept.presentation.ui.compose.PhotoCreateText
import mikes.dept.presentation.ui.compose.PhotoCreateTextInput
import mikes.dept.presentation.ui.core.BaseComposeFragment

class PhotoCreateFragment : BaseComposeFragment<PhotoCreateViewModel>() {

    private val pickMediaLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            viewModel.onImageSelected(uri = uri)
        }
    }

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
    @OptIn(ExperimentalComposeUiApi::class, ExperimentalComposeApi::class)
    @Composable
    private fun ComposeContentView(viewModel: PhotoCreateViewModel) {
        Column(modifier = Modifier.fillMaxSize()) {
            val captureController = rememberCaptureController()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(viewModel.backgroundColor.collectAsStateWithLifecycle().value)
                    .weight(1f)
                    .then(
                        when {
                            viewModel.contentSize.collectAsStateWithLifecycle().value != null -> Modifier
                            else -> Modifier.onGloballyPositioned { layoutCoordinates ->
                                viewModel.updateContentSize(size = layoutCoordinates.size.width)
                            }
                        }
                    )
                    .capturable(captureController)
            ) {
                if (viewModel.imageLastSelected.collectAsStateWithLifecycle().value) {
                    AsyncImage(
                        model = viewModel.image.collectAsStateWithLifecycle().value,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                PhotoCreateText(
                    textSettings = viewModel.textSettings.collectAsStateWithLifecycle(),
                    onTextOffsetChanged = { offset -> viewModel.onTextOffsetChanged(offset = offset) },
                    onTextSizeChanged = { textUnit -> viewModel.onTextSizeChanged(textUnit = textUnit) }
                )
            }
            PhotoCreateTextInput(
                value = viewModel.text.collectAsStateWithLifecycle(),
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
                    color = viewModel.backgroundColor.collectAsStateWithLifecycle(),
                    textResId = R.string.photo_create_button_background,
                    onClick = { viewModel.onChangeBackground() },
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                ColorWithTextButton(
                    color = viewModel.textColor.collectAsStateWithLifecycle(),
                    textResId = R.string.photo_create_button_text_color,
                    onClick = { viewModel.onChangeTextColor() },
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                ImageWithTextButton(
                    image = viewModel.image.collectAsStateWithLifecycle(),
                    textResId = R.string.photo_create_button_image,
                    onClick = { pickMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
            val scope = rememberCoroutineScope()
            Button(
                onClick = {
                    scope.launch {
                        runCatching {
                            val bitmapAsync = captureController.captureAsync()
                            val bitmap = bitmapAsync.await()
                            viewModel.onClickCreatePhoto(bitmap = bitmap.asAndroidBitmap())
                        }
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 32.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.btn_create_photo),
                    modifier = Modifier.padding(8.dp)
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

package mikes.dept.presentation.ui.photocreate

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import mikes.dept.presentation.di.core.SubcomponentProvider
import mikes.dept.presentation.ui.core.BaseComposeFragment
import kotlin.math.roundToInt

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

    @Composable
    private fun ComposeContentView(viewModel: PhotoCreateViewModel) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            var offset by remember { mutableStateOf(Offset(0f, 0f)) }
            var textSize by remember { mutableStateOf(20.sp) }
            Text(
                text = "Drag me", // TODO: change text
                fontSize = textSize,
                color = Color.Red,
                modifier = Modifier
                    .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
                    .wrapContentSize()
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            offset = Offset(offset.x + pan.x, offset.y + pan.y)
                            textSize *= zoom
                        }
                    }
            )
        }
    }

    @Preview
    @Composable
    private fun ComposeContentPreview() {
        ComposeContentView(viewModel = PhotoCreateViewModelComposable())
    }

}

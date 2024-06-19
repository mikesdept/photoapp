package mikes.dept.presentation.ui.photolist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import mikes.dept.presentation.R
import mikes.dept.presentation.di.core.SubcomponentProvider
import mikes.dept.presentation.ui.core.BaseComposeFragment

class PhotoListFragment : BaseComposeFragment<PhotoListViewModel>() {

    override fun initDagger(subcomponentProvider: SubcomponentProvider) = subcomponentProvider
        .providePhotoListSubcomponent()
        .inject(this)

    override fun initViewModel(viewModelProvider: ViewModelProvider): PhotoListViewModel =
        viewModelProvider[PhotoListViewModelImpl::class.java]

    @Composable
    override fun ComposeContent() {
        ComposeContentView(viewModel = viewModel)
    }

    override fun setup() {}

    @Composable
    private fun ComposeContentView(viewModel: PhotoListViewModel) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            Button(
                onClick = { viewModel.onClickCreatePhoto() },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
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
        ComposeContentView(viewModel = PhotoListViewModelComposable())
    }

}

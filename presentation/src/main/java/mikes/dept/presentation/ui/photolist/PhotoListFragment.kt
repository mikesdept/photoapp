package mikes.dept.presentation.ui.photolist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
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

    private companion object {
        private const val GRID_CELL_COUNT = 3
    }

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
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(count = GRID_CELL_COUNT),
                contentPadding = PaddingValues(bottom = 100.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                // TODO: fetch real data
                items(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26)) { item ->
                    PhotoItem(photoId = item)
                }
            }
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

    // TODO: change image UI
    @Composable
    private fun PhotoItem(
        photoId: Int,
        modifier: Modifier = Modifier
    ) {
        Box(
            modifier = modifier
                .aspectRatio(getPhotoAspectRatioById(id = photoId))
                .background(
                    color = Color.Red,
                    shape = RoundedCornerShape(size = 16.dp)
                )
        ) {
            Text(
                text = photoId.toString(),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

    private fun getPhotoAspectRatioById(id: Int): Float = when {
        (id - 3) % 10 == 0 || (id - 6) % 10 == 0 -> 0.5f
        else -> 1f
    }

    @Preview
    @Composable
    private fun ComposeContentPreview() {
        ComposeContentView(viewModel = PhotoListViewModelComposable())
    }

}

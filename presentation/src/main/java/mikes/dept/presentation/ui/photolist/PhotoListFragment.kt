package mikes.dept.presentation.ui.photolist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.Flow
import mikes.dept.domain.entities.PhotoEntity
import mikes.dept.presentation.R
import mikes.dept.presentation.di.core.SubcomponentProvider
import mikes.dept.presentation.ui.core.navdirections.NavDirectionsComposeFragment
import mikes.dept.presentation.ui.core.navdirections.event.ErrorEvent

class PhotoListFragment : NavDirectionsComposeFragment<PhotoListViewModel>() {

    private companion object {
        private const val GRID_CELL_COUNT = 2
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

    @Composable
    private fun ComposeContentView(viewModel: PhotoListViewModel) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            val photos = viewModel.photos.collectAndHandleError()
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(count = GRID_CELL_COUNT),
                contentPadding = PaddingValues(bottom = 100.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(count = photos.itemCount) { index ->
                    photos[index]?.let { photoEntity ->
                        PhotoItem(index = index, photoEntity = photoEntity)
                    }
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

    @Composable
    private fun PhotoItem(
        index: Int,
        photoEntity: PhotoEntity,
        modifier: Modifier = Modifier
    ) {
        AsyncImage(
            model = photoEntity.smallUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .aspectRatio(getPhotoAspectRatioById(id = index + 1))
                .clickable {}
        )
    }

    private fun getPhotoAspectRatioById(id: Int): Float = when {
        (id - 3) % 10 == 0 || (id - 9) % 10 == 0 -> 0.5f
        else -> 1f
    }

    @Composable
    private fun Flow<PagingData<PhotoEntity>>.collectAndHandleError(): LazyPagingItems<PhotoEntity> {
        val photos = collectAsLazyPagingItems()
        val loadStateMediator = photos.loadState.mediator
        LaunchedEffect(loadStateMediator) {
            val loadStateError = loadStateMediator?.append as? LoadState.Error
                ?: loadStateMediator?.prepend as? LoadState.Error
                ?: loadStateMediator?.refresh as? LoadState.Error
            val errorMessage = loadStateError?.error?.localizedMessage
            if (errorMessage != null) {
                showError(errorEvent = ErrorEvent.StringMessage(message = errorMessage))
            }
        }
        return photos
    }

    @Preview
    @Composable
    private fun ComposeContentPreview() {
        ComposeContentView(viewModel = PhotoListViewModelComposable())
    }

}

package mikes.dept.presentation.ui.photolist

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import mikes.dept.domain.entities.PhotoEntity
import mikes.dept.domain.repository.PhotoRepository
import mikes.dept.presentation.ui.core.navdirections.NavDirectionsViewModel
import mikes.dept.presentation.ui.core.navdirections.NavDirectionsViewModelImpl
import mikes.dept.presentation.ui.core.navdirections.event.ErrorEvent
import mikes.dept.presentation.ui.core.navdirections.event.NavDirectionsEvent
import javax.inject.Inject

interface PhotoListViewModel : NavDirectionsViewModel {

    val photos: Flow<PagingData<PhotoEntity>>

    fun onClickCreatePhoto()

}

class PhotoListViewModelImpl @Inject constructor(
    photoRepository: PhotoRepository<PagingData<PhotoEntity>>
) : NavDirectionsViewModelImpl(), PhotoListViewModel {

    override val photos: Flow<PagingData<PhotoEntity>> = photoRepository
        .getRemotePhotos()
        .cachedIn(viewModelScope)

    override fun onClickCreatePhoto() {
        val navDirectionsEvent = NavDirectionsEvent.Directions(
            navDirections = PhotoListFragmentDirections.navPhotoCreateFragment()
        )
        navigate(navDirectionsEvent = navDirectionsEvent)
    }

}

class PhotoListViewModelComposable : PhotoListViewModel {

    override val navDirections: Flow<NavDirectionsEvent> = flowOf()

    override val error: Flow<ErrorEvent> = flowOf()

    override val photos: Flow<PagingData<PhotoEntity>> = flowOf()

    override fun onClickCreatePhoto() {}

}

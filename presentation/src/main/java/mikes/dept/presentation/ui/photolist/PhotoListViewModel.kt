package mikes.dept.presentation.ui.photolist

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
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

    fun changeToCacheOnlyDataSource()
    fun onClickCreatePhoto()

}

class PhotoListViewModelImpl @Inject constructor(
    private val photoRepository: PhotoRepository<PagingData<PhotoEntity>>
) : NavDirectionsViewModelImpl(), PhotoListViewModel {

    private val cacheOnlyDataSource: MutableStateFlow<Boolean> = MutableStateFlow(false)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val photos: Flow<PagingData<PhotoEntity>> = cacheOnlyDataSource
        .flatMapLatest { cacheOnly -> getPhotos(cacheOnly = cacheOnly) }
        .cachedIn(viewModelScope)

    private fun getPhotos(cacheOnly: Boolean): Flow<PagingData<PhotoEntity>> = when {
        cacheOnly -> photoRepository.getLocalCachePhotos()
        else -> photoRepository.getRemotePhotos()
    }

    override fun changeToCacheOnlyDataSource() {
        cacheOnlyDataSource.value = true
    }

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

    override fun changeToCacheOnlyDataSource() {}

    override fun onClickCreatePhoto() {}

}

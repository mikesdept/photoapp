package mikes.dept.presentation.ui.photolist

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import mikes.dept.presentation.ui.core.navdirections.NavDirectionsViewModel
import mikes.dept.presentation.ui.core.navdirections.NavDirectionsViewModelImpl
import mikes.dept.presentation.ui.core.navdirections.event.ErrorEvent
import mikes.dept.presentation.ui.core.navdirections.event.NavDirectionsEvent
import javax.inject.Inject

interface PhotoListViewModel : NavDirectionsViewModel {

    fun onClickCreatePhoto()

}

class PhotoListViewModelImpl @Inject constructor() : NavDirectionsViewModelImpl(), PhotoListViewModel {

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

    override fun onClickCreatePhoto() {}

}

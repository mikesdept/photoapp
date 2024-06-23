package mikes.dept.presentation.ui.photocreate

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import mikes.dept.presentation.ui.core.navdirections.NavDirectionsViewModel
import mikes.dept.presentation.ui.core.navdirections.NavDirectionsViewModelImpl
import mikes.dept.presentation.ui.core.navdirections.event.ErrorEvent
import mikes.dept.presentation.ui.core.navdirections.event.NavDirectionsEvent
import javax.inject.Inject

interface PhotoCreateViewModel : NavDirectionsViewModel

class PhotoCreateViewModelImpl @Inject constructor() : NavDirectionsViewModelImpl(), PhotoCreateViewModel

class PhotoCreateViewModelComposable : PhotoCreateViewModel {

    override val navDirections: Flow<NavDirectionsEvent> = flowOf()

    override val error: Flow<ErrorEvent> = flowOf()

}

package mikes.dept.presentation.ui.core.navdirections

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import mikes.dept.presentation.ui.core.BaseViewModel
import mikes.dept.presentation.ui.core.IViewModel
import mikes.dept.presentation.ui.core.navdirections.event.ErrorEvent
import mikes.dept.presentation.ui.core.navdirections.event.NavDirectionsEvent

interface NavDirectionsViewModel : IViewModel {

    val navDirections: Flow<NavDirectionsEvent>
    val error: Flow<ErrorEvent>
}

open class NavDirectionsViewModelImpl : BaseViewModel(), NavDirectionsViewModel {

    override val navDirections: SharedFlow<NavDirectionsEvent> get() = _navDirections.asSharedFlow()
    private val _navDirections: MutableSharedFlow<NavDirectionsEvent> = MutableSharedFlow(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override val error: SharedFlow<ErrorEvent> get() = _error.asSharedFlow()
    private val _error: MutableSharedFlow<ErrorEvent> = MutableSharedFlow(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    protected fun navigate(navDirectionsEvent: NavDirectionsEvent) {
        _navDirections.tryEmit(navDirectionsEvent)
    }

    protected fun showError(errorEvent: ErrorEvent) {
        _error.tryEmit(errorEvent)
    }

}

package mikes.dept.presentation.ui.core.navdirections

import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collectLatest
import mikes.dept.presentation.ui.core.BaseComposeFragment
import mikes.dept.presentation.ui.core.navdirections.event.NavDirectionsEvent
import mikes.dept.presentation.utils.repeatOnLifecycleStarted

abstract class NavDirectionsComposeFragment<VM : NavDirectionsViewModel> : BaseComposeFragment<VM>() {

    override fun setup() {
        repeatOnLifecycleStarted {
            viewModel.navDirections.collectLatest { navDirectionsEvent -> navigate(navDirectionsEvent = navDirectionsEvent) }
        }
        repeatOnLifecycleStarted {
            viewModel.error.collectLatest { errorEvent -> showError(errorEvent = errorEvent) }
        }
    }

    private fun navigate(navDirectionsEvent: NavDirectionsEvent) {
        when (navDirectionsEvent) {
            is NavDirectionsEvent.Directions -> findNavController().navigate(navDirectionsEvent.navDirections)
            is NavDirectionsEvent.PopBackStack -> findNavController().popBackStack()
        }
    }

}

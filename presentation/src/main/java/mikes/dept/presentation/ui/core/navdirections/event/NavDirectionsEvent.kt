package mikes.dept.presentation.ui.core.navdirections.event

import androidx.navigation.NavDirections

sealed class NavDirectionsEvent {

    data object PopBackStack : NavDirectionsEvent()

    data class Directions(val navDirections: NavDirections) : NavDirectionsEvent()

}

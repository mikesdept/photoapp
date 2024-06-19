package mikes.dept.presentation.ui.core.navdirections.event

import androidx.annotation.StringRes

sealed class ErrorEvent {

    data class StringMessage(val message: String) : ErrorEvent()

    data class ResIdMessage(@StringRes val message: Int) : ErrorEvent()

}

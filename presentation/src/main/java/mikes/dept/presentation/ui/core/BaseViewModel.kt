package mikes.dept.presentation.ui.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.isActive

abstract class BaseViewModel : ViewModel() {

    protected fun <T> doSingleAction(
        action: suspend () -> T,
        onFailure: (Throwable) -> Unit,
        onSuccess: (T) -> Unit,
        onStart: () -> Unit,
        onComplete: () -> Unit,
    ) {
        action.asFlow()
            .onStart { onStart() }
            .onEach { onSuccess(it) }
            .catch { onFailure(it) }
            .onCompletion { if (viewModelScope.isActive) onComplete() }
            .launchIn(viewModelScope)
    }
}

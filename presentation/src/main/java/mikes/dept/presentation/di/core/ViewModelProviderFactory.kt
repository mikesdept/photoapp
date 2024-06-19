package mikes.dept.presentation.di.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mikes.dept.presentation.ui.core.BaseViewModel
import javax.inject.Inject
import javax.inject.Provider

class ViewModelProviderFactory @Inject constructor(
    private val factories: Map<Class<*>, @JvmSuppressWildcards Provider<BaseViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return factories[modelClass]?.get() as T
    }
    
}

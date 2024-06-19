package mikes.dept.presentation.ui.core

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mikes.dept.presentation.di.core.SubcomponentProvider
import mikes.dept.presentation.di.core.ViewModelProviderFactory
import mikes.dept.presentation.ui.core.navdirections.event.ErrorEvent
import javax.inject.Inject

abstract class BaseComposeFragment<VM : IViewModel> : Fragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private var _viewModel: VM? = null

    protected val viewModel: VM get() = _viewModel!!

    abstract fun initDagger(subcomponentProvider: SubcomponentProvider)

    open fun initViewModelProvider(): ViewModelProvider = ViewModelProvider(this, viewModelProviderFactory)

    abstract fun initViewModel(viewModelProvider: ViewModelProvider): VM

    @Composable
    abstract fun ComposeContent()

    abstract fun setup()

    open fun reset() {}

    protected fun showError(errorEvent: ErrorEvent) {
        val errorMessage = when (errorEvent) {
            is ErrorEvent.StringMessage -> errorEvent.message
            is ErrorEvent.ResIdMessage -> getString(errorEvent.message)
        }
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDagger(requireActivity().applicationContext as SubcomponentProvider)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            MaterialTheme {
                Surface {
                    ComposeContent()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewModel = initViewModel(initViewModelProvider())
        setup()
    }

    override fun onDestroyView() {
        reset()
        super.onDestroyView()
    }

}

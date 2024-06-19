package mikes.dept.presentation.ui.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import mikes.dept.presentation.di.core.ViewModelProviderFactory
import mikes.dept.presentation.di.core.SubcomponentProvider
import javax.inject.Inject

abstract class BaseActivity<VM : IViewModel, VB : ViewBinding> : AppCompatActivity() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private var _viewModel: VM? = null

    protected val viewModel: VM get() = _viewModel!!

    private var _binding: VB? = null

    protected val binding: VB get() = _binding!!

    abstract fun initDagger(subcomponentProvider: SubcomponentProvider)

    open fun initViewModelProvider(): ViewModelProvider = ViewModelProvider(this, viewModelProviderFactory)

    abstract fun initViewModel(viewModelProvider: ViewModelProvider): VM

    abstract fun getViewBinding(): VB

    abstract fun setup()

    abstract fun reset()

    override fun onCreate(savedInstanceState: Bundle?) {
        initDagger(applicationContext as SubcomponentProvider)
        super.onCreate(savedInstanceState)
        _viewModel = initViewModel(initViewModelProvider())
        _binding = getViewBinding()
        setContentView(_binding?.root)
        setup()
    }

    override fun onDestroy() {
        super.onDestroy()
        reset()
    }

}

package mikes.dept.presentation.ui.main

import androidx.lifecycle.ViewModelProvider
import mikes.dept.presentation.databinding.ActivityMainBinding
import mikes.dept.presentation.di.core.SubcomponentProvider
import mikes.dept.presentation.ui.core.BaseActivity

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override fun initDagger(subcomponentProvider: SubcomponentProvider) = subcomponentProvider
        .provideMainSubcomponent()
        .inject(this)

    override fun initViewModel(viewModelProvider: ViewModelProvider): MainViewModel = viewModelProvider[MainViewModelImpl::class.java]

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun setup() {}

    override fun reset() {}

}

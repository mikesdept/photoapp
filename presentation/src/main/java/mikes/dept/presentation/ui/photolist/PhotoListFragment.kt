package mikes.dept.presentation.ui.photolist

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import mikes.dept.presentation.di.core.SubcomponentProvider
import mikes.dept.presentation.ui.core.BaseComposeFragment

class PhotoListFragment : BaseComposeFragment<PhotoListViewModel>() {

    override fun initDagger(subcomponentProvider: SubcomponentProvider) = subcomponentProvider
        .providePhotoListSubcomponent()
        .inject(this)

    override fun initViewModel(viewModelProvider: ViewModelProvider): PhotoListViewModel =
        viewModelProvider[PhotoListViewModelImpl::class.java]

    @Composable
    override fun ComposeContent() {}

    override fun setup() {}

}

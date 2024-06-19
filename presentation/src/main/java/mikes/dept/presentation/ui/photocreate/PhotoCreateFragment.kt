package mikes.dept.presentation.ui.photocreate

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import mikes.dept.presentation.di.core.SubcomponentProvider
import mikes.dept.presentation.ui.core.BaseComposeFragment

class PhotoCreateFragment : BaseComposeFragment<PhotoCreateViewModel>() {

    override fun initDagger(subcomponentProvider: SubcomponentProvider) = subcomponentProvider
        .providePhotoCreateSubcomponent()
        .inject(this)

    override fun initViewModel(viewModelProvider: ViewModelProvider): PhotoCreateViewModel =
        viewModelProvider[PhotoCreateViewModelImpl::class.java]

    @Composable
    override fun ComposeContent() {}

    override fun setup() {}

}

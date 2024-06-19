package mikes.dept.presentation.ui.photocreate

import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import mikes.dept.presentation.ui.core.BaseViewModel

@Subcomponent(modules = [PhotoCreateModule::class])
interface PhotoCreateSubcomponent {

    @Subcomponent.Builder
    interface Builder {

        fun build(): PhotoCreateSubcomponent

    }

    fun inject(photoCreateFragment: PhotoCreateFragment)

}

@Module
interface PhotoCreateModule {

    @Binds
    @IntoMap
    @ClassKey(PhotoCreateViewModelImpl::class)
    fun bindViewModel(photoCreateViewModelImpl: PhotoCreateViewModelImpl): BaseViewModel

}

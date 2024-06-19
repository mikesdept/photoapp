package mikes.dept.presentation.ui.photolist

import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import mikes.dept.presentation.ui.core.BaseViewModel

@Subcomponent(modules = [PhotoListModule::class])
interface PhotoListSubcomponent {

    @Subcomponent.Builder
    interface Builder {

        fun build(): PhotoListSubcomponent

    }

    fun inject(photoListFragment: PhotoListFragment)

}

@Module
interface PhotoListModule {

    @Binds
    @IntoMap
    @ClassKey(PhotoListViewModelImpl::class)
    fun bindViewModel(photoListViewModelImpl: PhotoListViewModelImpl): BaseViewModel

}

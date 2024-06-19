package mikes.dept.photoapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import mikes.dept.presentation.ui.main.MainSubcomponent
import mikes.dept.presentation.ui.photocreate.PhotoCreateSubcomponent
import mikes.dept.presentation.ui.photolist.PhotoListSubcomponent
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent

    }

    fun mainSubcomponentBuilder(): MainSubcomponent.Builder

    fun photoListSubcomponentBuilder(): PhotoListSubcomponent.Builder

    fun photoCreateSubcomponentBuilder(): PhotoCreateSubcomponent.Builder

}

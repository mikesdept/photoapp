package mikes.dept.photoapp

import android.app.Application
import mikes.dept.photoapp.di.AppComponent
import mikes.dept.photoapp.di.DaggerAppComponent
import mikes.dept.presentation.di.core.SubcomponentProvider
import mikes.dept.presentation.ui.main.MainSubcomponent
import mikes.dept.presentation.ui.photocreate.PhotoCreateSubcomponent
import mikes.dept.presentation.ui.photolist.PhotoListSubcomponent

class App : Application(), SubcomponentProvider {

    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .context(applicationContext)
            .build()
    }

    override fun provideMainSubcomponent(): MainSubcomponent = appComponent.mainSubcomponentBuilder().build()

    override fun providePhotoListSubcomponent(): PhotoListSubcomponent = appComponent.photoListSubcomponentBuilder().build()

    override fun providePhotoCreateSubcomponent(): PhotoCreateSubcomponent = appComponent.photoCreateSubcomponentBuilder().build()

}

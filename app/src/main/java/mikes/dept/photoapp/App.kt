package mikes.dept.photoapp

import android.app.Application
import mikes.dept.photoapp.di.AppComponent
import mikes.dept.photoapp.di.DaggerAppComponent
import mikes.dept.presentation.di.core.SubcomponentProvider

class App : Application(), SubcomponentProvider {

    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .context(applicationContext)
            .build()
    }

}

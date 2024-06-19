package mikes.dept.presentation.di.core

import mikes.dept.presentation.ui.main.MainSubcomponent

interface SubcomponentProvider {

    fun provideMainSubcomponent(): MainSubcomponent

}

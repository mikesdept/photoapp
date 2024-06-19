package mikes.dept.presentation.di.core

import mikes.dept.presentation.ui.main.MainSubcomponent
import mikes.dept.presentation.ui.photocreate.PhotoCreateSubcomponent
import mikes.dept.presentation.ui.photolist.PhotoListSubcomponent

interface SubcomponentProvider {

    fun provideMainSubcomponent(): MainSubcomponent

    fun providePhotoListSubcomponent(): PhotoListSubcomponent

    fun providePhotoCreateSubcomponent(): PhotoCreateSubcomponent

}

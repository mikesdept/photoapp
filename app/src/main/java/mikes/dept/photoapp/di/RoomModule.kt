package mikes.dept.photoapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import mikes.dept.data.database.PhotoAppDatabase
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun providePhotoAppDatabase(context: Context): PhotoAppDatabase = Room
        .databaseBuilder(context, PhotoAppDatabase::class.java, "photo_app_database")
        .build()

}

package mikes.dept.photoapp.di

import androidx.paging.PagingData
import dagger.Binds
import dagger.Module
import mikes.dept.data.PhotoRepositoryImpl
import mikes.dept.data.datasource.PhotoNetworkDataSource
import mikes.dept.data.datasource.PhotoNetworkMockDataSourceImpl
import mikes.dept.domain.entities.PhotoEntity
import mikes.dept.domain.repository.PhotoRepository

@Module
interface BindModule {

    @Binds
    fun bindPhotoNetworkDataSource(photoNetworkMockDataSourceImpl: PhotoNetworkMockDataSourceImpl): PhotoNetworkDataSource

    @Binds
    fun bindPhotoRepository(photoRepositoryImpl: PhotoRepositoryImpl): PhotoRepository<PagingData<PhotoEntity>>

}

package mikes.dept.photoapp.di

import dagger.Module

@Module(
    includes = [
        NetworkModule::class,
        RoomModule::class
    ]
)
interface AppModule

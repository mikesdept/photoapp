package mikes.dept.photoapp.di

import dagger.Module

@Module(
    includes = [
        NetworkModule::class,
        RoomModule::class,
        BindModule::class
    ]
)
interface AppModule

package com.example.protodatastore.di

import com.example.protodatastore.users.datastore.IUserPrefsProtoDataStoreManager
import com.example.protodatastore.users.datastore.UserPrefsProtoDataStoreManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class UserPrefsProtoDataStoreManagerModule {

    @Singleton
    @Binds
    abstract fun bindUserPrefsProtoDataStoreManager(
            impl: UserPrefsProtoDataStoreManagerImpl
    ): IUserPrefsProtoDataStoreManager

}
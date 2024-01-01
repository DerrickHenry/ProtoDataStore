package com.example.protodatastore.di

import com.example.protodatastore.users.api.service.UserApi
import com.example.protodatastore.users.datastore.IUserPrefsProtoDataStoreManager
import com.example.protodatastore.users.repository.IUserRepository
import com.example.protodatastore.users.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UserRepoModule {

    @Provides
    @Singleton
    fun provideUserRepository(
            userApi: UserApi,
            protoDataStoreManager: IUserPrefsProtoDataStoreManager
    ): IUserRepository {
        return UserRepositoryImpl(userApi, protoDataStoreManager)
    }
}
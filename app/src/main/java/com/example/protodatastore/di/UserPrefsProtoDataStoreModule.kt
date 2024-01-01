package com.example.protodatastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.protodatastore.UserPreferences
import com.example.protodatastore.users.datastore.UserPrefsSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UserPrefsProtoDataStoreModule {

    private val Context.userPrefsProtoDataStore by dataStore(
            fileName = "user_prefs.pb",
            serializer = UserPrefsSerializer
    )

   @Provides
   @Singleton
   fun provideUserPrefsProtoDataStore(@ApplicationContext app: Context) : DataStore<UserPreferences> {
       return app.userPrefsProtoDataStore
   }
}
package com.example.protodatastore.users.datastore

import com.example.protodatastore.users.api.models.User
import kotlinx.coroutines.flow.Flow

interface IUserPrefsProtoDataStoreManager {
    suspend fun saveUserToProtoDataStore(user: User)
    suspend fun getUserFromProtoDataStore(): Flow<User>
}
package com.example.protodatastore.users.repository

import com.example.protodatastore.users.api.models.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    suspend fun getUsers(): List<User>
    suspend fun getUserFromProtoDataStore(): Flow<User>
    suspend fun saveUserToProtoDataStore(user: User)
}
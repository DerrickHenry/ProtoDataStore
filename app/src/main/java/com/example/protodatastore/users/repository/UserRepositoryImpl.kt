package com.example.protodatastore.users.repository

import com.example.protodatastore.users.api.models.User
import com.example.protodatastore.users.api.service.UserApi
import com.example.protodatastore.users.datastore.IUserPrefsProtoDataStoreManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
        private val userApi: UserApi,
        private val protoDataStoreMng: IUserPrefsProtoDataStoreManager
) : IUserRepository {
    override suspend fun getUsers(): List<User> {
        return userApi.getUsers(results = "1000").results
    }

    override suspend fun getUserFromProtoDataStore(): Flow<User> {
        return protoDataStoreMng.getUserFromProtoDataStore()
    }

    override suspend fun saveUserToProtoDataStore(user: User) {
        protoDataStoreMng.saveUserToProtoDataStore(user)
    }
}
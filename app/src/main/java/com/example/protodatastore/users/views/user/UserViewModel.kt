package com.example.protodatastore.users.views.user

import androidx.lifecycle.ViewModel
import com.example.protodatastore.users.api.models.User
import com.example.protodatastore.users.repository.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
        private val repository: IUserRepository
) : ViewModel() {

    suspend fun getUser(): Flow<User> {
        return repository.getUserFromProtoDataStore()
    }
}
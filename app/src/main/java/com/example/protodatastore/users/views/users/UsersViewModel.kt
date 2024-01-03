package com.example.protodatastore.users.views.users

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.protodatastore.users.api.Resource
import com.example.protodatastore.users.api.models.User
import com.example.protodatastore.users.repository.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
        private val repository: IUserRepository
) : ViewModel() {

    private val _users = MutableStateFlow<Resource<List<User>>>(Resource.Loading())
    val users: StateFlow<Resource<List<User>>> = _users

    private val usersCoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _users.value = Resource.Error(
                message = throwable.message ?: "something went wrong"
        )
    }

    init {
        getUsers()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getUsers() {
        viewModelScope.launch {
            _users.value = Resource.Loading()
            viewModelScope.launch(usersCoroutineExceptionHandler) {
                _users.value = Resource.Success(
                        data = repository.getUsers()
                )
            }
        }
    }

    fun saveUserToProtoDataStore(user: User) {
        viewModelScope.launch {
            repository.saveUserToProtoDataStore(user)
        }
    }

}
package com.example.protodatastore.users.views.user

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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
        private val repository: IUserRepository
) : ViewModel() {

    private val _user = MutableStateFlow<Resource<User>>(Resource.Loading())
    val user: StateFlow<Resource<User>> = _user

    private val userCoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _user.value = Resource.Error(
                message = throwable.message ?: "something went wrong with protobuf deserialization"
        )
    }

    init {
        getUser()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getUser() {
        viewModelScope.launch {
            _user.emit(Resource.Loading())
            viewModelScope.launch(userCoroutineExceptionHandler) {
                _user.emit(Resource.Success(
                        data = repository.getUserFromProtoDataStore().first()
                ))
            }
        }
    }
}
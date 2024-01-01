package com.example.protodatastore.users.views.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.protodatastore.users.api.models.User
import com.example.protodatastore.users.repository.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
        private val repository: IUserRepository
) : ViewModel() {

    private val _user = MutableStateFlow(User())
    val user: StateFlow<User> = _user

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            _user.emit(repository.getUserFromProtoDataStore().first())
        }
    }
}
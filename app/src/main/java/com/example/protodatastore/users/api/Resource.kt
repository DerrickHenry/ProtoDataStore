package com.example.protodatastore.users.api

// Wrapper/container for network responses
sealed class Resource<T>(
        val response: T? = null,
        val message: String? = null
) {
    class Loading<T> : Resource<T>()
    class Success<T>(response: T) : Resource<T>(response)
    class Error<T>(message: String) : Resource<T>(null, message)
}
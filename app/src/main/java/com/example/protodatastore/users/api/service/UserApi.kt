package com.example.protodatastore.users.api.service

import com.example.protodatastore.users.api.models.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    @GET("api")
    suspend fun getUsers(
            @Query("results") results: String
    ): UserResponse
}
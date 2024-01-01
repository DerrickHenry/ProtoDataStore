package com.example.protodatastore.users.api.models

data class UserResponse(
        val results: List<User>
)

data class User(
        val name: UserName,
        val location: UserLocation,
        val email: String = "",
        val picture: UserPicture
)

data class UserName(
        val first: String,
        val last: String
)

data class UserLocation(val city: String)

data class UserPicture(val large: String)
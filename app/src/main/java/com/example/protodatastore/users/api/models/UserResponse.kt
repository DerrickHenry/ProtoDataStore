package com.example.protodatastore.users.api.models

data class UserResponse(
        val results: List<User>
)

data class User(
        val name: UserName? = null,
        val location: UserLocation? = null,
        val email: String = "",
        val picture: UserPicture? = null,
        val dob: UserDob? = null,
        val gender: String = "",
        val registered: UserRegistered? = null
)

data class UserName(
        val first: String,
        val last: String
)

data class UserLocation(val city: String)

data class UserPicture(val large: String)

data class UserDob(val age: Int)

data class UserRegistered(val date: String)
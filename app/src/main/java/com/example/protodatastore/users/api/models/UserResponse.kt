package com.example.protodatastore.users.api.models

data class UserResponse(
        val results: List<User>
)

data class User(
        val name: UserName? = null,
        val location: UserLocation? = null,
        val email: String = "",
        val picture: UserPicture? = null,
        val dob: Age? = null,
        val gender: String = ""
)

data class UserName(
        val first: String,
        val last: String
)

data class UserLocation(val city: String)

data class UserPicture(val large: String)

data class Age(val age: Int)
package com.example.protodatastore.users.datastore

import androidx.datastore.core.DataStore
import com.example.protodatastore.UserPreferences
import com.example.protodatastore.users.api.models.User
import com.example.protodatastore.users.api.models.UserLocation
import com.example.protodatastore.users.api.models.UserName
import com.example.protodatastore.users.api.models.UserPicture
import com.example.protodatastore.users.api.models.UserRegistered
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPrefsProtoDataStoreManagerImpl @Inject constructor(
        private val protoDataStore: DataStore<UserPreferences>
) : IUserPrefsProtoDataStoreManager {

    // We want to serialize (via proto builder) the user obj into the ProtoDataStore
    override suspend fun saveUserToProtoDataStore(user: User) {
        protoDataStore.updateData { currentUser ->
            currentUser.toBuilder()
                    .setFirstName(user.name?.first)
                    .setLastName(user.name?.last)
                    .setCity(user.location?.city)
                    .setProfilePic(user.picture?.large)
                    .setDateRegistered(user.registered?.date)
                    .build()
        }

    }

    // We want to deserialize to protoDataStore user to client data class obj
    override suspend fun getUserFromProtoDataStore(): Flow<User> {
        return protoDataStore.data.map {
            User(
                    name = UserName(first = it.firstName, last = it.lastName),
                    location = UserLocation(city = it.city),
                    picture = UserPicture(it.profilePic),
                    registered = UserRegistered(it.dateRegistered)
            )

        }
    }
}
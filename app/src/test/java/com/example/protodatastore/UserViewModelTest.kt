package com.example.protodatastore

import com.example.protodatastore.users.api.models.User
import com.example.protodatastore.users.api.models.UserLocation
import com.example.protodatastore.users.api.models.UserName
import com.example.protodatastore.users.api.models.UserPicture
import com.example.protodatastore.users.repository.IUserRepository
import com.example.protodatastore.users.views.user.UserViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModelTest {

    @MockK
    private lateinit var mockRepository: IUserRepository

    private lateinit var viewModel: UserViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        MockKAnnotations.init(this)
        viewModel = UserViewModel(mockRepository)
    }

    @After
    fun afterTest() {
        unmockkAll()
    }


    @Test
    fun `should get user from proto data store and emit success state`() = runTest {
        val expectedMockProtoUser = User(
                name = UserName(first = "Jane", last = "Doe"),
                location = UserLocation(city = "Indianapolis"),
                picture = UserPicture("../users/?user=jane.doe.thumbnail.jpg")
        )

        // Return mock data every time vm Makes the call to getUserFromProtoDataStore()
        coEvery { mockRepository.getUserFromProtoDataStore() } returns flowOf(expectedMockProtoUser)

        viewModel.getUser()

        advanceUntilIdle()

        val actualUser = viewModel.user.value.data

        assertEquals(expectedMockProtoUser, actualUser)
    }

    @Test
    fun `test formatDate with valid date`() {
        val date = "2023-10-26T00:00:00.000Z"
        val formattedDate = viewModel.formatDate(date)
        assertEquals("October 2023", formattedDate)
    }

    @Test
    fun `test formatDate with invalid date`() {
        val date = "invalid date"
        val formattedDate = viewModel.formatDate(date)
        assertEquals("n/a", formattedDate)
    }
}
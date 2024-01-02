package com.example.protodatastore

import com.example.protodatastore.users.api.models.UserDob
import com.example.protodatastore.users.api.models.User
import com.example.protodatastore.users.api.models.UserName
import com.example.protodatastore.users.repository.IUserRepository
import com.example.protodatastore.users.views.users.UsersViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UsersViewModelTest {

    @MockK
    private lateinit var mockRepository: IUserRepository

    private lateinit var viewModel: UsersViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        MockKAnnotations.init(this)
        viewModel = UsersViewModel(mockRepository)
    }

    @After
    fun afterTest() {
        unmockkAll()
    }

    @Test
    fun `should get users and emit success state`() = runTest {
        // Set up some mock data
        val mockUser1 = User(gender = "male", name = UserName(first = "Padrino", last = "Taj"), dob = UserDob(age = 49))
        val mockUser2 = User(gender = "male", name = UserName(first = "Bop", last = "Alvarez"), dob = UserDob(age = 35))
        val mockUser3 = User(gender = "female", name = UserName(first = "Lisa", last = "Lopez"), dob = UserDob(age = 22))

        val expectedMockUsers = listOf(mockUser1, mockUser2, mockUser3)

        // have the repo return the data when vm calls it
        coEvery { mockRepository.getUsers() } returns expectedMockUsers

        // make the call
        viewModel.getUsers()

        advanceUntilIdle()

        // verify the call was made
        coVerify { mockRepository.getUsers() }

        val actualUsers = viewModel.users.value.data

        // assert the behavior
        assertEquals(expectedMockUsers, actualUsers)
    }

}

package com.ndejje.internee

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ndejje.internee.data.AppRepository
import com.ndejje.internee.data.User
import com.ndejje.internee.ui.viewmodel.AuthState
import com.ndejje.internee.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class AuthViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: AuthViewModel
    private val repository: AppRepository = mock()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = AuthViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login with correct credentials sets success state`() = runTest {
        val user = User(id = 1, name = "Test", email = "test@test.com", password = "password", role = "STUDENT")
        whenever(repository.getUserByEmail("test@test.com")).thenReturn(user)

        viewModel.login("test@test.com", "password")
        advanceUntilIdle()

        assertTrue(viewModel.authState.value is AuthState.Success)
        assertEquals(user, (viewModel.authState.value as AuthState.Success).user)
    }

    @Test
    fun `login with incorrect credentials sets error state`() = runTest {
        whenever(repository.getUserByEmail("test@test.com")).thenReturn(null)

        viewModel.login("test@test.com", "wrong")
        advanceUntilIdle()

        assertTrue(viewModel.authState.value is AuthState.Error)
    }

    @Test
    fun `register new user sets success state`() = runTest {
        whenever(repository.getUserByEmail("new@test.com")).thenReturn(null)
        
        viewModel.register("New User", "new@test.com", "password", "STUDENT")
        advanceUntilIdle()

        verify(repository).insertUser(any())
        assertTrue(viewModel.authState.value is AuthState.Success)
    }
}

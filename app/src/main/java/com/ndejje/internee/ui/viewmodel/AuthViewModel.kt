package com.ndejje.internee.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ndejje.internee.data.AppRepository
import com.ndejje.internee.data.User
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AppRepository) : ViewModel() {
    private val _currentUser = mutableStateOf<User?>(null)
    val currentUser: State<User?> = _currentUser

    private val _authState = mutableStateOf<AuthState>(AuthState.Idle)
    val authState: State<AuthState> = _authState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val user = repository.getUserByEmail(email)
            if (user != null && user.password == password) {
                _currentUser.value = user
                _authState.value = AuthState.Success(user)
            } else {
                _authState.value = AuthState.Error("Invalid credentials")
            }
        }
    }

    fun register(name: String, email: String, password: String, role: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val existingUser = repository.getUserByEmail(email)
            if (existingUser == null) {
                val newUser = User(name = name, email = email, password = password, role = role)
                repository.insertUser(newUser)
                _currentUser.value = newUser
                _authState.value = AuthState.Success(newUser)
            } else {
                _authState.value = AuthState.Error("User already exists")
            }
        }
    }

    fun logout() {
        _currentUser.value = null
        _authState.value = AuthState.Idle
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
}

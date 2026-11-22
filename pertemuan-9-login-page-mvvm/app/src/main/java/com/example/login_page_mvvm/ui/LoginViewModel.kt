package com.example.login_page_mvvm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.login_page_mvvm.data.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _loginResult = MutableStateFlow<LoginResult?>(null)
    val loginResult: StateFlow<LoginResult?> = _loginResult.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            repository.checkInitialData()
        }
    }

    fun onUsernameChange(newUsername: String) {
        _username.value = newUsername
        _loginResult.value = null
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        _loginResult.value = null
    }

    fun login() {
        val currentUsername = _username.value.trim()
        val currentPassword = _password.value.trim()

        if (currentUsername.isEmpty() || currentPassword.isEmpty()) {
            _loginResult.value = LoginResult.Error("Username dan Password tidak boleh kosong!")
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            val user = repository.login(currentUsername, currentPassword)
            _isLoading.value = false

            if (user != null) {
                _loginResult.value = LoginResult.Success(user.username)
            } else {
                _loginResult.value = LoginResult.Error("Username atau Password Salah\nCoba lagi!")
            }
        }
    }

    class Factory(private val repository: UserRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                return LoginViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

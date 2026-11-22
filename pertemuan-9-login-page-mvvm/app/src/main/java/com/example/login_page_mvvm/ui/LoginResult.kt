package com.example.login_page_mvvm.ui

sealed class LoginResult {
    data class Success(val username: String) : LoginResult()
    data class Error(val message: String) : LoginResult()
}

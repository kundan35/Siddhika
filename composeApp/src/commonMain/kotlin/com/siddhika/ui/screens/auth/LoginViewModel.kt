package com.siddhika.ui.screens.auth

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.siddhika.domain.usecase.auth.SignInWithEmailUseCase
import com.siddhika.domain.usecase.auth.SignInWithGoogleUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val signInWithEmailUseCase: SignInWithEmailUseCase,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase
) : ScreenModel {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isGoogleLoading = MutableStateFlow(false)
    val isGoogleLoading: StateFlow<Boolean> = _isGoogleLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _loginSuccess = MutableSharedFlow<Unit>()
    val loginSuccess: SharedFlow<Unit> = _loginSuccess.asSharedFlow()

    fun updateEmail(value: String) {
        _email.value = value
        _errorMessage.value = null
    }

    fun updatePassword(value: String) {
        _password.value = value
        _errorMessage.value = null
    }

    fun signInWithEmail() {
        if (!validateInput()) return

        screenModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            signInWithEmailUseCase(_email.value.trim(), _password.value)
                .onSuccess {
                    _loginSuccess.emit(Unit)
                }
                .onFailure { e ->
                    _errorMessage.value = e.message ?: "Sign in failed"
                }

            _isLoading.value = false
        }
    }

    fun signInWithGoogle(idToken: String) {
        screenModelScope.launch {
            _isGoogleLoading.value = true
            _errorMessage.value = null

            signInWithGoogleUseCase(idToken)
                .onSuccess {
                    _loginSuccess.emit(Unit)
                }
                .onFailure { e ->
                    _errorMessage.value = e.message ?: "Google sign in failed"
                }

            _isGoogleLoading.value = false
        }
    }

    fun onGoogleSignInRequested(onRequest: () -> Unit) {
        _isGoogleLoading.value = true
        onRequest()
    }

    fun onGoogleSignInCancelled() {
        _isGoogleLoading.value = false
    }

    fun clearError() {
        _errorMessage.value = null
    }

    private fun validateInput(): Boolean {
        val emailValue = _email.value.trim()
        val passwordValue = _password.value

        return when {
            emailValue.isEmpty() -> {
                _errorMessage.value = "Please enter your email"
                false
            }
            !emailValue.contains("@") -> {
                _errorMessage.value = "Please enter a valid email"
                false
            }
            passwordValue.isEmpty() -> {
                _errorMessage.value = "Please enter your password"
                false
            }
            passwordValue.length < 6 -> {
                _errorMessage.value = "Password must be at least 6 characters"
                false
            }
            else -> true
        }
    }
}

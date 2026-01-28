package com.siddhika.ui.screens.auth

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.siddhika.domain.usecase.auth.SignUpWithEmailUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val signUpWithEmailUseCase: SignUpWithEmailUseCase
) : ScreenModel {

    private val _displayName = MutableStateFlow("")
    val displayName: StateFlow<String> = _displayName.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _registerSuccess = MutableSharedFlow<Unit>()
    val registerSuccess: SharedFlow<Unit> = _registerSuccess.asSharedFlow()

    fun updateDisplayName(value: String) {
        _displayName.value = value
        _errorMessage.value = null
    }

    fun updateEmail(value: String) {
        _email.value = value
        _errorMessage.value = null
    }

    fun updatePassword(value: String) {
        _password.value = value
        _errorMessage.value = null
    }

    fun updateConfirmPassword(value: String) {
        _confirmPassword.value = value
        _errorMessage.value = null
    }

    fun register() {
        if (!validateInput()) return

        screenModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val name = _displayName.value.trim().ifEmpty { null }

            signUpWithEmailUseCase(_email.value.trim(), _password.value, name)
                .onSuccess {
                    _registerSuccess.emit(Unit)
                }
                .onFailure { e ->
                    _errorMessage.value = e.message ?: "Registration failed"
                }

            _isLoading.value = false
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }

    private fun validateInput(): Boolean {
        val emailValue = _email.value.trim()
        val passwordValue = _password.value
        val confirmPasswordValue = _confirmPassword.value

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
                _errorMessage.value = "Please enter a password"
                false
            }
            passwordValue.length < 6 -> {
                _errorMessage.value = "Password must be at least 6 characters"
                false
            }
            confirmPasswordValue != passwordValue -> {
                _errorMessage.value = "Passwords do not match"
                false
            }
            else -> true
        }
    }
}

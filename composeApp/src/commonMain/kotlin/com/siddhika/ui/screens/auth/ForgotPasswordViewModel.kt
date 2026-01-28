package com.siddhika.ui.screens.auth

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.siddhika.domain.usecase.auth.SendPasswordResetUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val sendPasswordResetUseCase: SendPasswordResetUseCase
) : ScreenModel {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _resetEmailSent = MutableSharedFlow<Unit>()
    val resetEmailSent: SharedFlow<Unit> = _resetEmailSent.asSharedFlow()

    fun updateEmail(value: String) {
        _email.value = value
        _errorMessage.value = null
    }

    fun sendResetEmail() {
        if (!validateInput()) return

        screenModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            sendPasswordResetUseCase(_email.value.trim())
                .onSuccess {
                    _resetEmailSent.emit(Unit)
                }
                .onFailure { e ->
                    _errorMessage.value = e.message ?: "Failed to send reset email"
                }

            _isLoading.value = false
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }

    private fun validateInput(): Boolean {
        val emailValue = _email.value.trim()

        return when {
            emailValue.isEmpty() -> {
                _errorMessage.value = "Please enter your email"
                false
            }
            !emailValue.contains("@") -> {
                _errorMessage.value = "Please enter a valid email"
                false
            }
            else -> true
        }
    }
}

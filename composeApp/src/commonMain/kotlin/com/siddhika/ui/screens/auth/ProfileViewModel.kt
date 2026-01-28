package com.siddhika.ui.screens.auth

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.siddhika.core.util.AuthState
import com.siddhika.domain.model.User
import com.siddhika.domain.usecase.auth.DeleteAccountUseCase
import com.siddhika.domain.usecase.auth.GetAuthStateUseCase
import com.siddhika.domain.usecase.auth.SendEmailVerificationUseCase
import com.siddhika.domain.usecase.auth.SignOutUseCase
import com.siddhika.domain.usecase.auth.UpdateProfileUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(
    getAuthStateUseCase: GetAuthStateUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val sendEmailVerificationUseCase: SendEmailVerificationUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase
) : ScreenModel {

    val user: StateFlow<User?> = getAuthStateUseCase()
        .map { state ->
            when (state) {
                is AuthState.Authenticated -> state.user
                else -> null
            }
        }
        .stateIn(
            scope = screenModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isEditingName = MutableStateFlow(false)
    val isEditingName: StateFlow<Boolean> = _isEditingName.asStateFlow()

    private val _editedName = MutableStateFlow("")
    val editedName: StateFlow<String> = _editedName.asStateFlow()

    private val _showDeleteConfirmation = MutableStateFlow(false)
    val showDeleteConfirmation: StateFlow<Boolean> = _showDeleteConfirmation.asStateFlow()

    private val _message = MutableSharedFlow<String>()
    val message: SharedFlow<String> = _message.asSharedFlow()

    private val _signedOut = MutableSharedFlow<Unit>()
    val signedOut: SharedFlow<Unit> = _signedOut.asSharedFlow()

    fun startEditingName() {
        _editedName.value = user.value?.displayName ?: ""
        _isEditingName.value = true
    }

    fun cancelEditingName() {
        _isEditingName.value = false
        _editedName.value = ""
    }

    fun updateEditedName(value: String) {
        _editedName.value = value
    }

    fun saveDisplayName() {
        val newName = _editedName.value.trim()
        if (newName.isEmpty()) {
            screenModelScope.launch {
                _message.emit("Display name cannot be empty")
            }
            return
        }

        screenModelScope.launch {
            _isLoading.value = true

            updateProfileUseCase(newName, null)
                .onSuccess {
                    _message.emit("Profile updated successfully")
                    _isEditingName.value = false
                }
                .onFailure { e ->
                    _message.emit(e.message ?: "Failed to update profile")
                }

            _isLoading.value = false
        }
    }

    fun sendEmailVerification() {
        screenModelScope.launch {
            _isLoading.value = true

            sendEmailVerificationUseCase()
                .onSuccess {
                    _message.emit("Verification email sent! Check your inbox.")
                }
                .onFailure { e ->
                    _message.emit(e.message ?: "Failed to send verification email")
                }

            _isLoading.value = false
        }
    }

    fun signOut() {
        screenModelScope.launch {
            _isLoading.value = true

            signOutUseCase()
                .onSuccess {
                    _signedOut.emit(Unit)
                }
                .onFailure { e ->
                    _message.emit(e.message ?: "Failed to sign out")
                }

            _isLoading.value = false
        }
    }

    fun showDeleteConfirmation() {
        _showDeleteConfirmation.value = true
    }

    fun hideDeleteConfirmation() {
        _showDeleteConfirmation.value = false
    }

    fun deleteAccount() {
        screenModelScope.launch {
            _isLoading.value = true
            _showDeleteConfirmation.value = false

            deleteAccountUseCase()
                .onSuccess {
                    _signedOut.emit(Unit)
                }
                .onFailure { e ->
                    _message.emit(e.message ?: "Failed to delete account")
                }

            _isLoading.value = false
        }
    }
}

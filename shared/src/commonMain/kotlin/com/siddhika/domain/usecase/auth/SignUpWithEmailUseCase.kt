package com.siddhika.domain.usecase.auth

import com.siddhika.domain.model.User
import com.siddhika.domain.repository.AuthRepository

class SignUpWithEmailUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        displayName: String? = null
    ): Result<User> = repository.signUpWithEmail(email, password, displayName)
}

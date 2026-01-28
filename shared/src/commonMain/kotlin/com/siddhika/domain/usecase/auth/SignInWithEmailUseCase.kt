package com.siddhika.domain.usecase.auth

import com.siddhika.domain.model.User
import com.siddhika.domain.repository.AuthRepository

class SignInWithEmailUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> =
        repository.signInWithEmail(email, password)
}

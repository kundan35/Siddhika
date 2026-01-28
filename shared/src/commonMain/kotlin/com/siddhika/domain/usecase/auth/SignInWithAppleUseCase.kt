package com.siddhika.domain.usecase.auth

import com.siddhika.domain.model.User
import com.siddhika.domain.repository.AuthRepository

class SignInWithAppleUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(idToken: String, nonce: String): Result<User> =
        repository.signInWithApple(idToken, nonce)
}

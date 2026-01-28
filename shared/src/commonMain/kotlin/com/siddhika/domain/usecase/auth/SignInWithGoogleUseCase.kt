package com.siddhika.domain.usecase.auth

import com.siddhika.domain.model.User
import com.siddhika.domain.repository.AuthRepository

class SignInWithGoogleUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(idToken: String): Result<User> =
        repository.signInWithGoogle(idToken)
}

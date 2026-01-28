package com.siddhika.domain.usecase.auth

import com.siddhika.domain.repository.AuthRepository

class SendPasswordResetUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String): Result<Unit> =
        repository.sendPasswordResetEmail(email)
}

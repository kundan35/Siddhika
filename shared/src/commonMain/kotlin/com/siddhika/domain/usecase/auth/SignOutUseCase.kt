package com.siddhika.domain.usecase.auth

import com.siddhika.domain.repository.AuthRepository

class SignOutUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit> = repository.signOut()
}

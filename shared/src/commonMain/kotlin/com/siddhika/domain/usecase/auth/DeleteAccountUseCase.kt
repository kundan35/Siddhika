package com.siddhika.domain.usecase.auth

import com.siddhika.domain.repository.AuthRepository

class DeleteAccountUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit> = repository.deleteAccount()
}

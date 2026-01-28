package com.siddhika.domain.usecase.auth

import com.siddhika.core.util.AuthState
import com.siddhika.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class GetAuthStateUseCase(
    private val repository: AuthRepository
) {
    operator fun invoke(): Flow<AuthState> = repository.authState
}

package com.siddhika.domain.usecase.auth

import com.siddhika.domain.model.User
import com.siddhika.domain.repository.AuthRepository

class UpdateProfileUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(displayName: String?, photoUrl: String?): Result<User> =
        repository.updateProfile(displayName, photoUrl)
}

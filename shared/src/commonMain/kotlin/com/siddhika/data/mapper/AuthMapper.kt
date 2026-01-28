package com.siddhika.data.mapper

import com.siddhika.data.auth.model.FirebaseAuthUser
import com.siddhika.domain.model.AuthProvider
import com.siddhika.domain.model.User

fun FirebaseAuthUser.toDomain(): User = User(
    id = uid,
    email = email,
    displayName = displayName,
    photoUrl = photoUrl,
    isEmailVerified = isEmailVerified,
    provider = when (providerId) {
        "google.com" -> AuthProvider.GOOGLE
        "apple.com" -> AuthProvider.APPLE
        else -> AuthProvider.EMAIL
    }
)

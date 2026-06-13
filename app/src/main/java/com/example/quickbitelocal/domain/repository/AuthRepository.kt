package com.example.quickbitelocal.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val isUserLoggedIn: Boolean
    fun login(email: String, password: String): Flow<Result<Unit>>
    fun signUp(email: String, password: String): Flow<Result<Unit>>
    fun logout()
}

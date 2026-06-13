package com.example.quickbitelocal.domain.repository

import com.example.quickbitelocal.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getCurrentUser(): Flow<User?>
    suspend fun saveUser(user: User)
    suspend fun clearUser()
}

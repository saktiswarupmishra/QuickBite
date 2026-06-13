package com.example.quickbitelocal.data.repository

import com.example.quickbitelocal.data.local.dao.UserDao
import com.example.quickbitelocal.domain.model.User
import com.example.quickbitelocal.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val firebaseAuth: FirebaseAuth
) : UserRepository {

    override fun getCurrentUser(): Flow<User?> {
        val currentFirebaseUser = firebaseAuth.currentUser
        return if (currentFirebaseUser != null) {
            userDao.getUserById(currentFirebaseUser.uid)
        } else {
            flowOf(null)
        }
    }

    override suspend fun saveUser(user: User) {
        userDao.insertUser(user)
    }

    override suspend fun clearUser() {
        userDao.clearUser()
    }
}

package com.example.quickbitelocal.data.repository

import com.example.quickbitelocal.domain.model.User
import com.example.quickbitelocal.domain.repository.AuthRepository
import com.example.quickbitelocal.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userRepository: UserRepository
) : AuthRepository {

    override val isUserLoggedIn: Boolean
        get() = firebaseAuth.currentUser != null

    override fun login(email: String, password: String): Flow<Result<Unit>> = callbackFlow {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val firebaseUser = result.user
                if (firebaseUser != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        userRepository.saveUser(
                            User(
                                id = firebaseUser.uid,
                                name = firebaseUser.displayName ?: email.split("@")[0],
                                email = firebaseUser.email ?: email
                            )
                        )
                    }
                }
                trySend(Result.success(Unit))
                close()
            }
            .addOnFailureListener { e ->
                val error = if (e.message?.contains("CONFIGURATION_NOT_FOUND") == true) {
                    Exception("Please enable 'Email/Password' in Firebase Console > Authentication > Sign-in method")
                } else e
                trySend(Result.failure(error))
                close()
            }
        awaitClose()
    }

    override fun signUp(email: String, password: String): Flow<Result<Unit>> = callbackFlow {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val firebaseUser = result.user
                if (firebaseUser != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        userRepository.saveUser(
                            User(
                                id = firebaseUser.uid,
                                name = email.split("@")[0],
                                email = firebaseUser.email ?: email
                            )
                        )
                    }
                }
                trySend(Result.success(Unit))
                close()
            }
            .addOnFailureListener { e ->
                val error = if (e.message?.contains("CONFIGURATION_NOT_FOUND") == true) {
                    Exception("Please enable 'Email/Password' in Firebase Console > Authentication > Sign-in method")
                } else e
                trySend(Result.failure(error))
                close()
            }
        awaitClose()
    }

    override fun logout() {
        CoroutineScope(Dispatchers.IO).launch {
            userRepository.clearUser()
        }
        firebaseAuth.signOut()
    }
}

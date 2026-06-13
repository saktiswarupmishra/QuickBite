package com.example.quickbitelocal.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: String,
    val name: String,
    val email: String,
    val phoneNumber: String = "",
    val profilePictureUrl: String = ""
)

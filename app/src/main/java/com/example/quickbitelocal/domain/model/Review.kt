package com.example.quickbitelocal.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reviews")
data class Review(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val restaurantId: String,
    val userName: String,
    val rating: Int,
    val comment: String,
    val timestamp: Long = System.currentTimeMillis()
)

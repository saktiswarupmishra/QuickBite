package com.example.quickbitelocal.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurants")
data class Restaurant(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val rating: Double,
    val deliveryTime: String,
    val cuisine: String,
    val isFavorite: Boolean = false
)

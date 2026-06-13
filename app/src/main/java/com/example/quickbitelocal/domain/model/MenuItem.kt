package com.example.quickbitelocal.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menu_items")
data class MenuItem(
    @PrimaryKey val id: String,
    val restaurantId: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val isAvailable: Boolean = true
)

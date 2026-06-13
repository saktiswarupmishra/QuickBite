package com.example.quickbitelocal.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey val id: String,
    val restaurantId: String,
    val restaurantName: String,
    val totalAmount: Double,
    val status: String, // PENDING, CONFIRMED, DELIVERED
    val timestamp: Long
)

@Entity(tableName = "order_items")
data class OrderItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val orderId: String,
    val menuItemId: String,
    val name: String,
    val price: Double,
    val quantity: Int
)

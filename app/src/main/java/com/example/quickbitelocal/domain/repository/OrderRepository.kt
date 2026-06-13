package com.example.quickbitelocal.domain.repository

import com.example.quickbitelocal.domain.model.CartItem
import com.example.quickbitelocal.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getOrders(): Flow<List<Order>>
    suspend fun placeOrder(restaurantId: String, restaurantName: String, totalAmount: Double, cartItems: List<CartItem>): String
}

package com.example.quickbitelocal.domain.repository

import com.example.quickbitelocal.domain.model.CartItem
import com.example.quickbitelocal.domain.model.MenuItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartItems(): Flow<List<CartItem>>
    suspend fun addToCart(menuItem: MenuItem)
    suspend fun removeFromCart(cartItem: CartItem)
    suspend fun updateQuantity(menuItemId: String, quantity: Int)
    suspend fun clearCart()
}

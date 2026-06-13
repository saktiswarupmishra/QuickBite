package com.example.quickbitelocal.data.local.dao

import androidx.room.*
import com.example.quickbitelocal.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items")
    fun getCartItems(): Flow<List<CartItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(cartItem: CartItem)

    @Delete
    suspend fun removeFromCart(cartItem: CartItem)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

    @Query("UPDATE cart_items SET quantity = :quantity WHERE menuItemId = :menuItemId")
    suspend fun updateQuantity(menuItemId: String, quantity: Int)

    @Query("SELECT * FROM cart_items WHERE menuItemId = :menuItemId")
    suspend fun getCartItemById(menuItemId: String): CartItem?
}

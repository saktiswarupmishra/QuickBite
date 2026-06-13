package com.example.quickbitelocal.data.repository

import com.example.quickbitelocal.data.local.dao.CartDao
import com.example.quickbitelocal.domain.model.CartItem
import com.example.quickbitelocal.domain.model.MenuItem
import com.example.quickbitelocal.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {

    override fun getCartItems(): Flow<List<CartItem>> {
        return cartDao.getCartItems()
    }

    override suspend fun addToCart(menuItem: MenuItem) {
        cartDao.addToCart(
            CartItem(
                menuItemId = menuItem.id,
                name = menuItem.name,
                price = menuItem.price,
                quantity = 1,
                restaurantId = menuItem.restaurantId
            )
        )
    }

    override suspend fun removeFromCart(cartItem: CartItem) {
        cartDao.removeFromCart(cartItem)
    }

    override suspend fun updateQuantity(menuItemId: String, quantity: Int) {
        if (quantity > 0) {
            cartDao.updateQuantity(menuItemId, quantity)
        } else {
            val cartItem = cartDao.getCartItemById(menuItemId)
            if (cartItem != null) {
                cartDao.removeFromCart(cartItem)
            }
        }
    }

    override suspend fun clearCart() {
        cartDao.clearCart()
    }
}

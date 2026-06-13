package com.example.quickbitelocal.data.repository

import com.example.quickbitelocal.data.local.dao.OrderDao
import com.example.quickbitelocal.domain.model.CartItem
import com.example.quickbitelocal.domain.model.Order
import com.example.quickbitelocal.domain.model.OrderItem
import com.example.quickbitelocal.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderDao: OrderDao
) : OrderRepository {

    override fun getOrders(): Flow<List<Order>> {
        return orderDao.getAllOrders()
    }

    override suspend fun placeOrder(
        restaurantId: String,
        restaurantName: String,
        totalAmount: Double,
        cartItems: List<CartItem>
    ): String {
        val orderId = UUID.randomUUID().toString()
        val order = Order(
            id = orderId,
            restaurantId = restaurantId,
            restaurantName = restaurantName,
            totalAmount = totalAmount,
            status = "CONFIRMED",
            timestamp = System.currentTimeMillis()
        )
        
        val orderItems = cartItems.map {
            OrderItem(
                orderId = orderId,
                menuItemId = it.menuItemId,
                name = it.name,
                price = it.price,
                quantity = it.quantity
            )
        }
        
        orderDao.insertOrder(order)
        orderDao.insertOrderItems(orderItems)
        
        return orderId
    }
}

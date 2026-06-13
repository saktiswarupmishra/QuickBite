package com.example.quickbitelocal.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.quickbitelocal.domain.model.Order
import com.example.quickbitelocal.domain.model.OrderItem
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Query("SELECT * FROM orders ORDER BY timestamp DESC")
    fun getAllOrders(): Flow<List<Order>>

    @Insert
    suspend fun insertOrder(order: Order)

    @Insert
    suspend fun insertOrderItems(items: List<OrderItem>)
}

package com.example.quickbitelocal.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.quickbitelocal.data.local.dao.AddressDao
import com.example.quickbitelocal.data.local.dao.CartDao
import com.example.quickbitelocal.data.local.dao.OrderDao
import com.example.quickbitelocal.data.local.dao.RestaurantDao
import com.example.quickbitelocal.data.local.dao.UserDao
import com.example.quickbitelocal.domain.model.Address
import com.example.quickbitelocal.domain.model.CartItem
import com.example.quickbitelocal.domain.model.MenuItem
import com.example.quickbitelocal.domain.model.Order
import com.example.quickbitelocal.domain.model.OrderItem
import com.example.quickbitelocal.domain.model.Restaurant
import com.example.quickbitelocal.domain.model.Review
import com.example.quickbitelocal.domain.model.User

@Database(
    entities = [Restaurant::class, MenuItem::class, CartItem::class, Order::class, OrderItem::class, Address::class, Review::class, User::class],
    version = 2,
    exportSchema = false
)
abstract class QuickBiteDatabase : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao
    abstract fun addressDao(): AddressDao
    abstract fun userDao(): UserDao
}

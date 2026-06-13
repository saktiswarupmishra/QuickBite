package com.example.quickbitelocal.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.quickbitelocal.domain.model.MenuItem
import com.example.quickbitelocal.domain.model.Restaurant
import com.example.quickbitelocal.domain.model.Review
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantDao {
    @Query("SELECT * FROM restaurants")
    fun getAllRestaurants(): Flow<List<Restaurant>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurants(restaurants: List<Restaurant>)

    @Query("SELECT * FROM restaurants WHERE id = :id")
    suspend fun getRestaurantById(id: String): Restaurant?

    @Query("SELECT * FROM menu_items WHERE restaurantId = :restaurantId")
    fun getMenuItems(restaurantId: String): Flow<List<MenuItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenuItems(menuItems: List<MenuItem>)

    @Query("SELECT * FROM reviews WHERE restaurantId = :restaurantId ORDER BY timestamp DESC")
    fun getReviews(restaurantId: String): Flow<List<Review>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReviews(reviews: List<Review>)

    @Query("UPDATE restaurants SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun toggleFavorite(id: String, isFavorite: Boolean)

    @Query("SELECT * FROM restaurants WHERE isFavorite = 1")
    fun getFavoriteRestaurants(): Flow<List<Restaurant>>
}

package com.example.quickbitelocal.domain.repository

import com.example.quickbitelocal.domain.model.MenuItem
import com.example.quickbitelocal.domain.model.Restaurant
import com.example.quickbitelocal.domain.model.Review
import kotlinx.coroutines.flow.Flow

interface RestaurantRepository {
    fun getRestaurants(): Flow<List<Restaurant>>
    suspend fun getRestaurantById(id: String): Restaurant?
    fun getMenuItems(restaurantId: String): Flow<List<MenuItem>>
    fun getReviews(restaurantId: String): Flow<List<Review>>
    suspend fun refreshRestaurants()
    suspend fun refreshMenuItems(restaurantId: String)
    suspend fun refreshReviews(restaurantId: String)
    suspend fun toggleFavorite(id: String, isFavorite: Boolean)
    fun getFavoriteRestaurants(): Flow<List<Restaurant>>
}

package com.example.quickbitelocal.data.repository

import com.example.quickbitelocal.data.local.dao.RestaurantDao
import com.example.quickbitelocal.domain.model.MenuItem
import com.example.quickbitelocal.domain.model.Restaurant
import com.example.quickbitelocal.domain.model.Review
import com.example.quickbitelocal.domain.repository.RestaurantRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RestaurantRepositoryImpl @Inject constructor(
    private val restaurantDao: RestaurantDao
) : RestaurantRepository {

    override fun getRestaurants(): Flow<List<Restaurant>> {
        return restaurantDao.getAllRestaurants()
    }

    override suspend fun getRestaurantById(id: String): Restaurant? {
        return restaurantDao.getRestaurantById(id)
    }

    override fun getMenuItems(restaurantId: String): Flow<List<MenuItem>> {
        return restaurantDao.getMenuItems(restaurantId)
    }

    override fun getReviews(restaurantId: String): Flow<List<Review>> {
        return restaurantDao.getReviews(restaurantId)
    }

    override suspend fun toggleFavorite(id: String, isFavorite: Boolean) {
        restaurantDao.toggleFavorite(id, isFavorite)
    }

    override fun getFavoriteRestaurants(): Flow<List<Restaurant>> {
        return restaurantDao.getFavoriteRestaurants()
    }

    override suspend fun refreshRestaurants() {
        // Mocking API call with real Unsplash URLs
        val dummyRestaurants = listOf(
            Restaurant("1", "Burger King", "Home of the Whopper", "https://images.unsplash.com/photo-1571091718767-18b5b1457add?w=500&q=80", 4.2, "30 mins", "Burgers, Fast Food"),
            Restaurant("2", "Pizza Hut", "Hut. Hut. Hut.", "https://images.unsplash.com/photo-1513104890138-7c749659a591?w=500&q=80", 4.5, "45 mins", "Pizza, Italian"),
            Restaurant("3", "Subway", "Eat Fresh", "https://images.unsplash.com/photo-1534353473418-4cfa6c56fd38?w=500&q=80", 4.0, "20 mins", "Sandwiches, Healthy")
        )
        restaurantDao.insertRestaurants(dummyRestaurants)
    }

    override suspend fun refreshMenuItems(restaurantId: String) {
        // Mocking API call for menu items with real food images
        val dummyMenuItems = when(restaurantId) {
            "1" -> listOf(
                MenuItem("1_1", "1", "Whopper", "Flame-grilled beef patty", 12.99, "https://images.unsplash.com/photo-1550547660-d9450f859349?w=300&q=80"),
                MenuItem("1_2", "1", "Cheeseburger", "Classic cheeseburger", 8.99, "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=300&q=80"),
                MenuItem("1_3", "1", "French Fries", "Crispy golden fries", 4.99, "https://images.unsplash.com/photo-1573088693280-9ee26259d72a?w=300&q=80")
            )
            "2" -> listOf(
                MenuItem("2_1", "2", "Pepperoni Pizza", "Classic pepperoni", 15.99, "https://images.unsplash.com/photo-1628840042765-356cda07504e?w=300&q=80"),
                MenuItem("2_2", "2", "Veggie Pizza", "Fresh vegetables", 14.99, "https://images.unsplash.com/photo-1571407970349-bc81e7e96d47?w=300&q=80"),
                MenuItem("2_3", "2", "Garlic Bread", "Buttery garlic bread", 5.99, "https://images.unsplash.com/photo-1573140247632-f8fd74997d5c?w=300&q=80")
            )
            else -> listOf(
                MenuItem("${restaurantId}_1", restaurantId, "Standard Item", "Description", 10.0, "https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=300&q=80")
            )
        }
        restaurantDao.insertMenuItems(dummyMenuItems)
    }

    override suspend fun refreshReviews(restaurantId: String) {
        // Mocking API call for reviews
        val dummyReviews = listOf(
            Review(restaurantId = restaurantId, userName = "John Doe", rating = 5, comment = "Best burgers in town!"),
            Review(restaurantId = restaurantId, userName = "Jane Smith", rating = 4, comment = "Good food, but delivery was a bit late."),
            Review(restaurantId = restaurantId, userName = "Mike Ross", rating = 5, comment = "Amazing experience, will order again!")
        )
        restaurantDao.insertReviews(dummyReviews)
    }
}

package com.example.quickbitelocal.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickbitelocal.domain.model.Restaurant
import com.example.quickbitelocal.domain.repository.OrderRepository
import com.example.quickbitelocal.domain.repository.RestaurantRepository
import com.google.firebase.ai.GenerativeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RestaurantRepository,
    private val orderRepository: OrderRepository,
    private val generativeModel: GenerativeModel
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _aiRecommendation = MutableStateFlow<String?>(null)
    val aiRecommendation: StateFlow<String?> = _aiRecommendation.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    val restaurants: StateFlow<List<Restaurant>> = combine(
        repository.getRestaurants(),
        _searchQuery,
        _selectedCategory
    ) { restaurants, query, category ->
        var filtered = restaurants
        if (query.isNotBlank()) {
            filtered = filtered.filter { 
                it.name.contains(query, ignoreCase = true) || 
                it.cuisine.contains(query, ignoreCase = true) 
            }
        }
        if (category != "All") {
            filtered = filtered.filter { it.cuisine.contains(category, ignoreCase = true) }
        }
        filtered
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            repository.refreshRestaurants()
            generateAiRecommendation()
        }
    }

    private fun generateAiRecommendation() {
        viewModelScope.launch {
            try {
                val orders = orderRepository.getOrders().first()
                val prompt = if (orders.isEmpty()) {
                    "Suggest a popular cuisine for a new food delivery app user in one short sentence."
                } else {
                    val orderHistory = orders.joinToString { "${it.restaurantName} (${it.totalAmount})" }
                    "Based on this order history: $orderHistory, suggest what the user might like to eat next in one very short sentence."
                }
                
                val response = generativeModel.generateContent(prompt)
                _aiRecommendation.value = response.text
            } catch (e: Exception) {
                _aiRecommendation.value = "Craving something delicious? Explore our top picks!"
            }
        }
    }

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun onCategorySelect(category: String) {
        _selectedCategory.value = category
    }

    fun toggleFavorite(restaurant: Restaurant) {
        viewModelScope.launch {
            repository.toggleFavorite(restaurant.id, !restaurant.isFavorite)
        }
    }
}

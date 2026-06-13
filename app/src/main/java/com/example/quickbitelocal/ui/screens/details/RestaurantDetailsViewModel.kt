package com.example.quickbitelocal.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickbitelocal.domain.model.MenuItem
import com.example.quickbitelocal.domain.model.Restaurant
import com.example.quickbitelocal.domain.model.Review
import com.example.quickbitelocal.domain.repository.CartRepository
import com.example.quickbitelocal.domain.repository.RestaurantRepository
import com.google.firebase.ai.GenerativeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RestaurantDetailsUiState(
    val restaurant: Restaurant? = null,
    val menuItems: List<MenuItem> = emptyList(),
    val reviews: List<Review> = emptyList(),
    val reviewSummary: String? = null,
    val isLoading: Boolean = false
)

@HiltViewModel
class RestaurantDetailsViewModel @Inject constructor(
    private val repository: RestaurantRepository,
    private val cartRepository: CartRepository,
    private val generativeModel: GenerativeModel,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val restaurantId: String = checkNotNull(savedStateHandle["restaurantId"])

    private val _uiState = MutableStateFlow(RestaurantDetailsUiState(isLoading = true))
    val uiState: StateFlow<RestaurantDetailsUiState> = _uiState.asStateFlow()

    init {
        loadRestaurantDetails()
    }

    private fun loadRestaurantDetails() {
        viewModelScope.launch {
            val restaurant = repository.getRestaurantById(restaurantId)
            repository.refreshMenuItems(restaurantId)
            repository.refreshReviews(restaurantId)
            
            combine(
                repository.getMenuItems(restaurantId),
                repository.getReviews(restaurantId)
            ) { items, reviews ->
                _uiState.update { it.copy(
                    restaurant = restaurant,
                    menuItems = items,
                    reviews = reviews,
                    isLoading = false
                ) }
                if (reviews.isNotEmpty() && _uiState.value.reviewSummary == null) {
                    generateReviewSummary(reviews)
                }
            }.collect()
        }
    }

    private fun generateReviewSummary(reviews: List<Review>) {
        viewModelScope.launch {
            try {
                val reviewsText = reviews.joinToString("\n") { "${it.userName}: ${it.comment} (${it.rating} stars)" }
                val prompt = "Summarize these restaurant reviews in one short, helpful sentence for a customer: \n$reviewsText"
                val response = generativeModel.generateContent(prompt)
                _uiState.update { it.copy(reviewSummary = response.text) }
            } catch (e: Exception) {
                // Ignore AI errors
            }
        }
    }

    fun addToCart(menuItem: MenuItem) {
        viewModelScope.launch {
            cartRepository.addToCart(menuItem)
        }
    }
}

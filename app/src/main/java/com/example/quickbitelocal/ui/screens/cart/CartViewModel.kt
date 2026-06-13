package com.example.quickbitelocal.ui.screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickbitelocal.domain.model.CartItem
import com.example.quickbitelocal.domain.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CartUiState(
    val items: List<CartItem> = emptyList(),
    val subtotal: Double = 0.0,
    val deliveryFee: Double = 2.0,
    val tax: Double = 0.0,
    val totalAmount: Double = 0.0,
    val appliedCoupon: String? = null,
    val discount: Double = 0.0
)

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    val uiState: StateFlow<CartUiState> = cartRepository.getCartItems()
        .map { items ->
            val subtotal = items.sumOf { it.price * it.quantity }
            val tax = subtotal * 0.05 // 5% tax
            val deliveryFee = if (subtotal > 0) 2.0 else 0.0
            CartUiState(
                items = items,
                subtotal = subtotal,
                tax = tax,
                deliveryFee = deliveryFee,
                totalAmount = subtotal + tax + deliveryFee
            )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CartUiState())

    fun applyCoupon(code: String) {
        // Simple logic: "SAVE10" gives $10 off
        if (code.uppercase() == "SAVE10") {
            // In a real app, this would be more complex
        }
    }

    fun updateQuantity(menuItemId: String, quantity: Int) {
        viewModelScope.launch {
            cartRepository.updateQuantity(menuItemId, quantity)
        }
    }

    fun removeFromCart(cartItem: CartItem) {
        viewModelScope.launch {
            cartRepository.removeFromCart(cartItem)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            cartRepository.clearCart()
        }
    }
}

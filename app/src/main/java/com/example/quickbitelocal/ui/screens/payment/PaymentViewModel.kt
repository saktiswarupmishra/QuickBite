package com.example.quickbitelocal.ui.screens.payment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickbitelocal.domain.repository.CartRepository
import com.example.quickbitelocal.domain.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PaymentUiState(
    val totalAmount: Double = 0.0,
    val selectedMethod: String = "UPI",
    val isProcessing: Boolean = false,
    val orderSuccessId: String? = null
)

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(PaymentUiState(
        totalAmount = savedStateHandle.get<String>("amount")?.toDoubleOrNull() ?: 0.0
    ))
    val uiState: StateFlow<PaymentUiState> = _uiState.asStateFlow()

    fun selectMethod(method: String) {
        _uiState.update { it.copy(selectedMethod = method) }
    }

    fun processPayment() {
        viewModelScope.launch {
            _uiState.update { it.copy(isProcessing = true) }
            
            // Simulate payment processing delay
            kotlinx.coroutines.delay(2000)

            val cartItems = cartRepository.getCartItems().run { 
                val items = mutableListOf<com.example.quickbitelocal.domain.model.CartItem>()
                collect { items.addAll(it) }
                items
            }
            
            if (cartItems.isNotEmpty()) {
                val restaurantId = cartItems.first().restaurantId
                val restaurantName = "QuickBite Partner"

                val orderId = orderRepository.placeOrder(
                    restaurantId = restaurantId,
                    restaurantName = restaurantName,
                    totalAmount = _uiState.value.totalAmount,
                    cartItems = cartItems
                )

                cartRepository.clearCart()
                _uiState.update { it.copy(isProcessing = false, orderSuccessId = orderId) }
            }
        }
    }
}

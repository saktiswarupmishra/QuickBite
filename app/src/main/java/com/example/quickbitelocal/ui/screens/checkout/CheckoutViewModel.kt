package com.example.quickbitelocal.ui.screens.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickbitelocal.domain.model.Address
import com.example.quickbitelocal.domain.model.CartItem
import com.example.quickbitelocal.domain.repository.AddressRepository
import com.example.quickbitelocal.domain.repository.CartRepository
import com.example.quickbitelocal.domain.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CheckoutUiState(
    val cartItems: List<CartItem> = emptyList(),
    val addresses: List<Address> = emptyList(),
    val selectedAddress: Address? = null,
    val totalAmount: Double = 0.0,
    val isPlacingOrder: Boolean = false,
    val orderSuccessId: String? = null
)

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository,
    private val addressRepository: AddressRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CheckoutUiState())
    val uiState: StateFlow<CheckoutUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                cartRepository.getCartItems(),
                addressRepository.getAddresses()
            ) { items, addresses ->
                val selected = _uiState.value.selectedAddress ?: addresses.find { it.isDefault } ?: addresses.firstOrNull()
                _uiState.update { it.copy(
                    cartItems = items,
                    addresses = addresses,
                    selectedAddress = selected,
                    totalAmount = items.sumOf { item -> item.price * item.quantity }
                ) }
            }.collect()
        }
    }

    fun selectAddress(address: Address) {
        _uiState.update { it.copy(selectedAddress = address) }
    }

    fun placeOrder() {
        val currentState = _uiState.value
        if (currentState.cartItems.isEmpty() || currentState.selectedAddress == null) return

        viewModelScope.launch {
            _uiState.update { it.copy(isPlacingOrder = true) }
            
            // In a real app, we'd get restaurant info properly
            val restaurantId = currentState.cartItems.first().restaurantId
            val restaurantName = "QuickBite Partner" 

            val orderId = orderRepository.placeOrder(
                restaurantId = restaurantId,
                restaurantName = restaurantName,
                totalAmount = currentState.totalAmount,
                cartItems = currentState.cartItems
            )
            
            cartRepository.clearCart()
            _uiState.update { it.copy(isPlacingOrder = false, orderSuccessId = orderId) }
        }
    }
}

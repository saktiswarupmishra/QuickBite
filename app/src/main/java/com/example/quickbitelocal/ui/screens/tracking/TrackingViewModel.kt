package com.example.quickbitelocal.ui.screens.tracking

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TrackingUiState(
    val orderId: String = "",
    val status: String = "Order Confirmed",
    val progress: Float = 0.2f,
    val estimatedTime: String = "25 mins",
    val restaurantLocation: LatLng = LatLng(12.9716, 77.5946), // Bangalore coordinates
    val userLocation: LatLng = LatLng(12.9352, 77.6245),
    val deliveryPartnerLocation: LatLng = LatLng(12.9716, 77.5946)
)

@HiltViewModel
class TrackingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(TrackingUiState(
        orderId = savedStateHandle.get<String>("orderId") ?: ""
    ))
    val uiState: StateFlow<TrackingUiState> = _uiState.asStateFlow()

    init {
        simulateTracking()
    }

    private fun simulateTracking() {
        viewModelScope.launch {
            delay(5000)
            _uiState.update { it.copy(
                status = "Preparing your food",
                progress = 0.5f,
                estimatedTime = "15 mins"
            ) }
            delay(5000)
            _uiState.update { it.copy(
                status = "Food is out for delivery",
                progress = 0.8f,
                estimatedTime = "5 mins",
                deliveryPartnerLocation = LatLng(12.9534, 77.6095)
            ) }
            delay(5000)
            _uiState.update { it.copy(
                status = "Enjoy your meal!",
                progress = 1.0f,
                estimatedTime = "Arrived",
                deliveryPartnerLocation = LatLng(12.9352, 77.6245)
            ) }
        }
    }
}

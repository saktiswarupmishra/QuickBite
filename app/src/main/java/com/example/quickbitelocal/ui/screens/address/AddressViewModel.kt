package com.example.quickbitelocal.ui.screens.address

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickbitelocal.domain.model.Address
import com.example.quickbitelocal.domain.repository.AddressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val addressRepository: AddressRepository
) : ViewModel() {

    val addresses: StateFlow<List<Address>> = addressRepository.getAddresses()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addAddress(label: String, fullAddress: String) {
        viewModelScope.launch {
            val isFirst = addresses.value.isEmpty()
            addressRepository.addAddress(
                Address(label = label, fullAddress = fullAddress, isDefault = isFirst)
            )
        }
    }

    fun deleteAddress(address: Address) {
        viewModelScope.launch {
            addressRepository.deleteAddress(address)
        }
    }

    fun setDefaultAddress(addressId: Int) {
        viewModelScope.launch {
            addressRepository.setDefaultAddress(addressId)
        }
    }
}

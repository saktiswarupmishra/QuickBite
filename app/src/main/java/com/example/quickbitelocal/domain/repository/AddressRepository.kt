package com.example.quickbitelocal.domain.repository

import com.example.quickbitelocal.domain.model.Address
import kotlinx.coroutines.flow.Flow

interface AddressRepository {
    fun getAddresses(): Flow<List<Address>>
    suspend fun addAddress(address: Address)
    suspend fun deleteAddress(address: Address)
    suspend fun setDefaultAddress(addressId: Int)
}

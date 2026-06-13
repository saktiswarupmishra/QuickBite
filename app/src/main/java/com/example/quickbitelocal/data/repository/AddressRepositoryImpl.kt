package com.example.quickbitelocal.data.repository

import com.example.quickbitelocal.data.local.dao.AddressDao
import com.example.quickbitelocal.domain.model.Address
import com.example.quickbitelocal.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(
    private val addressDao: AddressDao
) : AddressRepository {

    override fun getAddresses(): Flow<List<Address>> {
        return addressDao.getAllAddresses()
    }

    override suspend fun addAddress(address: Address) {
        addressDao.insertAddress(address)
    }

    override suspend fun deleteAddress(address: Address) {
        addressDao.deleteAddress(address)
    }

    override suspend fun setDefaultAddress(addressId: Int) {
        addressDao.updateDefaultAddress(addressId)
    }
}

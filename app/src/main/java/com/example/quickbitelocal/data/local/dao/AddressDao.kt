package com.example.quickbitelocal.data.local.dao

import androidx.room.*
import com.example.quickbitelocal.domain.model.Address
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressDao {
    @Query("SELECT * FROM addresses")
    fun getAllAddresses(): Flow<List<Address>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddress(address: Address)

    @Delete
    suspend fun deleteAddress(address: Address)

    @Query("UPDATE addresses SET isDefault = 0")
    suspend fun resetDefaultAddresses()

    @Query("UPDATE addresses SET isDefault = 1 WHERE id = :addressId")
    suspend fun setDefaultAddress(addressId: Int)

    @Transaction
    suspend fun updateDefaultAddress(addressId: Int) {
        resetDefaultAddresses()
        setDefaultAddress(addressId)
    }
}

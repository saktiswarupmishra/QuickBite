package com.example.quickbitelocal.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "addresses")
data class Address(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val label: String, // Home, Work, etc.
    val fullAddress: String,
    val isDefault: Boolean = false
)

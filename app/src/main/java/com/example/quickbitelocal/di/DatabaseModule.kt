package com.example.quickbitelocal.di

import android.content.Context
import androidx.room.Room
import com.example.quickbitelocal.data.local.QuickBiteDatabase
import com.example.quickbitelocal.data.local.dao.AddressDao
import com.example.quickbitelocal.data.local.dao.CartDao
import com.example.quickbitelocal.data.local.dao.OrderDao
import com.example.quickbitelocal.data.local.dao.RestaurantDao
import com.example.quickbitelocal.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): QuickBiteDatabase {
        return Room.databaseBuilder(
            context,
            QuickBiteDatabase::class.java,
            "quickbite_db"
        )
        .fallbackToDestructiveMigration() // Added to handle version change
        .build()
    }

    @Provides
    fun provideRestaurantDao(database: QuickBiteDatabase): RestaurantDao {
        return database.restaurantDao()
    }

    @Provides
    fun provideCartDao(database: QuickBiteDatabase): CartDao {
        return database.cartDao()
    }

    @Provides
    fun provideOrderDao(database: QuickBiteDatabase): OrderDao {
        return database.orderDao()
    }

    @Provides
    fun provideAddressDao(database: QuickBiteDatabase): AddressDao {
        return database.addressDao()
    }

    @Provides
    fun provideUserDao(database: QuickBiteDatabase): UserDao {
        return database.userDao()
    }
}

package com.example.quickbitelocal.di

import com.example.quickbitelocal.data.repository.AddressRepositoryImpl
import com.example.quickbitelocal.data.repository.AuthRepositoryImpl
import com.example.quickbitelocal.data.repository.CartRepositoryImpl
import com.example.quickbitelocal.data.repository.OrderRepositoryImpl
import com.example.quickbitelocal.data.repository.RestaurantRepositoryImpl
import com.example.quickbitelocal.data.repository.UserRepositoryImpl
import com.example.quickbitelocal.domain.repository.AddressRepository
import com.example.quickbitelocal.domain.repository.AuthRepository
import com.example.quickbitelocal.domain.repository.CartRepository
import com.example.quickbitelocal.domain.repository.OrderRepository
import com.example.quickbitelocal.domain.repository.RestaurantRepository
import com.example.quickbitelocal.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRestaurantRepository(
        restaurantRepositoryImpl: RestaurantRepositoryImpl
    ): RestaurantRepository

    @Binds
    @Singleton
    abstract fun bindCartRepository(
        cartRepositoryImpl: CartRepositoryImpl
    ): CartRepository

    @Binds
    @Singleton
    abstract fun bindOrderRepository(
        orderRepositoryImpl: OrderRepositoryImpl
    ): OrderRepository

    @Binds
    @Singleton
    abstract fun bindAddressRepository(
        addressRepositoryImpl: AddressRepositoryImpl
    ): AddressRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository
}

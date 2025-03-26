package com.deliveryapp.domain

import kotlinx.coroutines.flow.Flow

interface OrderRepository {

    fun fetchNewOrders() : List<Order>

    fun fetchCurrentOrders() : Flow<List<Order>>

    suspend fun insertCurrentOrder(order: Order)

    suspend fun deleteOrder(uid: Int)

}
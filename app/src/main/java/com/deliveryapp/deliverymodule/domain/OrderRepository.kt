package com.deliveryapp.deliverymodule.domain

import com.deliveryapp.deliverymodule.domain.model.FinishOrder
import com.deliveryapp.deliverymodule.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface OrderRepository {

    fun fetchNewOrders() : List<Order>

    fun fetchFinishOrders() : Flow<List<FinishOrder>>

    fun fetchCurrentOrders() : Flow<List<Order>>

    suspend fun insertCurrentOrder(order: Order)

    suspend fun insertFinishOrder(order: Order, isSuccessful: Boolean)

    suspend fun deleteCurrentOrder(uid: Int)

}
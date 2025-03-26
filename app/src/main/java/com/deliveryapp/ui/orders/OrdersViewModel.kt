package com.deliveryapp.ui.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deliveryapp.domain.Order
import com.deliveryapp.domain.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrdersViewModel(private val orderRepository: OrderRepository) : ViewModel() {

    private var _newOrdersList = MutableLiveData<List<Order>>()
    val newOrderList: LiveData<List<Order>> = _newOrdersList

    private var _currentOrdersList = MutableLiveData<List<Order>>()
    val currentList: LiveData<List<Order>> = _currentOrdersList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _newOrdersList.postValue(orderRepository.fetchNewOrders())
            orderRepository.fetchCurrentOrders().collect { it ->
                _currentOrdersList.postValue(it)
            }
        }
    }

    fun insertOrder(order: Order) {
        viewModelScope.launch(Dispatchers.IO) {
            orderRepository.insertCurrentOrder(order)
        }
    }

    fun deleteOrder(orderId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            orderRepository.deleteOrder(orderId)
        }
    }

}
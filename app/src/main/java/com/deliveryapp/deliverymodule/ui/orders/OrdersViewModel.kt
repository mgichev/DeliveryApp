package com.deliveryapp.deliverymodule.ui.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deliveryapp.deliverymodule.domain.repository.OrderRepository
import com.deliveryapp.deliverymodule.domain.model.FinishOrder
import com.deliveryapp.deliverymodule.domain.model.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrdersViewModel(private val orderRepository: OrderRepository) : ViewModel() {

    private var _newOrdersList = MutableLiveData<List<Order>>()
    val newOrderList: LiveData<List<Order>> = _newOrdersList

    private var _currentOrdersList = MutableLiveData<List<Order>>()
    val currentList: LiveData<List<Order>> = _currentOrdersList

    private var _finishOrdersList = MutableLiveData<List<FinishOrder>>()
    val finishList: LiveData<List<FinishOrder>> = _finishOrdersList

    private var _ordersToShow = MutableLiveData<List<Boolean>>()
    val ordersToShow: LiveData<List<Boolean>>
        get() = _ordersToShow

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _newOrdersList.postValue(orderRepository.fetchNewOrders())
            orderRepository.fetchCurrentOrders().collect {
                _currentOrdersList.postValue(it)
                _ordersToShow.postValue(List<Boolean>(it.size) { false })
            }

            orderRepository.fetchFinishOrders().collect {
                _finishOrdersList.postValue(it)
            }
        }
    }

    fun insertCurrentOrder(order: Order) {
        viewModelScope.launch(Dispatchers.IO) {
            orderRepository.insertCurrentOrder(order)
        }
    }

    fun deleteCurrentOrder(orderId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            orderRepository.deleteCurrentOrder(orderId)
        }
    }

    fun insertFinishOrder(order: Order) {
        viewModelScope.launch(Dispatchers.IO) {
            orderRepository.insertFinishOrder(order, true)
        }
    }

    fun setOrdersToShow(id: Int, isChecked: Boolean) {
        val list: MutableList<Boolean>? = _ordersToShow.value?.toMutableList()
        list?.let {
            it[id] = isChecked
        }
        _ordersToShow.value = list ?: listOf()
    }


}
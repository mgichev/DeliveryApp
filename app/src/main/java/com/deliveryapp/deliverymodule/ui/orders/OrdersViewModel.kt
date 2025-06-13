// Автор: Гичев М. А., КТбо4-8
// Тема: ВКР. Разработка мобильного приложения для работы курьера
// Описание: класс для хранения состояния экрана заказов и взаимодействия с бизнес-логикой

package com.deliveryapp.deliverymodule.ui.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deliveryapp.deliverymodule.domain.model.FinishOrder
import com.deliveryapp.deliverymodule.domain.model.Order
import com.deliveryapp.deliverymodule.domain.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel для экрана работы с заказами.
 * Управляет состоянием списков заказов (новые, текущие, завершенные)
 * и обеспечивает взаимодействие с репозиторием заказов.
 *
 * @property orderRepository Репозиторий для работы с данными заказов
 */
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

    /**
     * Добавляет заказ в список текущих.
     * @param order Заказ для добавления
     */
    fun insertCurrentOrder(order: Order) {
        viewModelScope.launch(Dispatchers.IO) {
            orderRepository.insertCurrentOrder(order)
        }
    }

    /**
     * Удаляет заказ из списка текущих.
     * @param orderId ID заказа для удаления
     */
    fun deleteCurrentOrder(orderId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            orderRepository.deleteCurrentOrder(orderId)
        }
    }

    /**
     * Переносит заказ в список завершенных.
     * @param order Заказ для отметки как завершенного
     */
    fun insertFinishOrder(order: Order) {
        viewModelScope.launch(Dispatchers.IO) {
            orderRepository.insertFinishOrder(order, true)
        }
    }

    /**
     * Устанавливает параметр отображения указанного заказа на карте.
     * @param id Индекс заказа в списке
     * @param isChecked Флаг необходимости отображения
     */
    fun setOrdersToShow(id: Int, isChecked: Boolean) {
        val list: MutableList<Boolean>? = _ordersToShow.value?.toMutableList()
        list?.let {
            it[id] = isChecked
        }
        _ordersToShow.value = list ?: listOf()
    }


}
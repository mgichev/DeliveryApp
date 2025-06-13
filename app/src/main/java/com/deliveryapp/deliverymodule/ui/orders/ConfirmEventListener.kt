// Автор: Гичев М. А., КТбо4-8
// Тема: ВКР. Разработка мобильного приложения для работы курьера
// Описание: интерфейс-слушатель для адаптера текущих заказов

package com.deliveryapp.deliverymodule.ui.orders

import com.deliveryapp.deliverymodule.domain.model.Order

interface ConfirmEventListener {

    /**
     * Вызывается при получении заказа и предоставляет элемент Order
     */
    fun onGetOrderBtnClicked(order: Order)

    /**
     * Вызывается при завершении заказа и предоставляет элемент Order
     */
    fun onFinishOrderBtnClicked(order: Order)
}
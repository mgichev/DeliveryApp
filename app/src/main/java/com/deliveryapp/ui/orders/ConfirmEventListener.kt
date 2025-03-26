package com.deliveryapp.ui.orders

import com.deliveryapp.domain.Order

interface ConfirmEventListener {
    fun onGetOrderBtnClicked(order: Order)
    fun onFinishOrderBtnClicked(order: Order)
}
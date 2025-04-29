package com.deliveryapp.deliverymodule.ui.orders

import com.deliveryapp.deliverymodule.domain.model.Order

interface ConfirmEventListener {
    fun onGetOrderBtnClicked(order: Order)
    fun onFinishOrderBtnClicked(order: Order)
}
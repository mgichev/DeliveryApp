package com.deliveryapp.deliverymodule.domain.model

data class FinishOrder(
    val title: String,
    val money: Int,
    val orderAddress: String,
    val isSuccessful: Boolean,
)
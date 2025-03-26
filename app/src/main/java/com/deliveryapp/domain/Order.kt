package com.deliveryapp.domain

import com.yandex.mapkit.geometry.Point

data class Order (
    val title: String,
    val shortDescription: String,
    val mapAddress: String,
    val point: Point,
    val date: String,
    val money: Int,
    val uid: Int? = null,
    val phoneNumber: String? = null,
) {
    init {

    }
}
package com.deliveryapp.domain

import com.example.deliveryapp.R
import com.yandex.mapkit.geometry.Point

class DeliveryPoint(
    val point: Point,
    val deliveryPointTypes: DeliveryPointTypes,
)

enum class DeliveryPointTypes(val imageDrawable: Int) {
    DEFAULT(R.drawable.box_24)
}
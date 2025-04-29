package com.deliveryapp.deliverymodule.domain.model

import com.example.deliveryapp.R
import com.yandex.mapkit.geometry.Point

class DeliveryPoint(
    val point: Point,
    val deliveryPointTypes: DeliveryPointTypes,
)

enum class DeliveryPointTypes(val imageDrawable: Int) {
    FROM_DELIVERY(R.drawable.box_24),
    TO_ORDER(R.drawable.delivery_metka_100)
}
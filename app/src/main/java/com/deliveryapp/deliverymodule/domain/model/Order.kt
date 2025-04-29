package com.deliveryapp.deliverymodule.domain.model

import com.yandex.mapkit.geometry.Point

data class Order (
    val title: String,
    val shortDescription: String,
    val fromMapAddress: String,
    val toMapAddress: String,
    val fromPoint: Point,
    val toPoint: Point,
    val date: String,
    val money: Int,
    val distance: Float,
    val uid: Int? = null,
    val phoneNumber: String? = null,
)
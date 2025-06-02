package com.deliveryapp.deliverymodule.domain

import com.deliveryapp.App
import com.yandex.mapkit.geometry.Point

fun createGlideLink(point1: Point, point2: Point): String {
    val api = App.STATIC_MAP_API
    val template =
        "https://static-maps.yandex.ru/v1?&pt=${point1.longitude},${point1.latitude},round1~${point2.longitude},${point2.latitude},round2&size=300,300&apikey=$api"
    return template
}
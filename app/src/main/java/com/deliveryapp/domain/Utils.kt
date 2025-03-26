package com.deliveryapp.domain

import com.deliveryapp.App

fun createGlideLink(lat: Double, long: Double): String {
    val api = App.STATIC_MAP_API
    val template =
        "https://static-maps.yandex.ru/v1?ll=$long,$lat&pt=$long,$lat&z=14&size=300,300&apikey=$api"
    return template
}
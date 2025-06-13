package com.deliveryapp.deliverymodule.domain.model

import android.graphics.Color

enum class Category(val displayName: String, val color: Int) {
    FRIDGE("Холодильник", Color.parseColor("#011BEA")),
    HEATER("Обогреватель", Color.parseColor("#EA4701")),
    FRAGILE("Хрупкое", Color.parseColor("#ACA3FF")),
    LARGE("Крупное", Color.parseColor("#251A58")),
    SMALL("Мелкое", Color.parseColor("#9D9FAF")),
    ORGANIC("Органика", Color.parseColor("#71D19E")),
    URGENT("Срочное", Color.parseColor("#FF0000"))
}
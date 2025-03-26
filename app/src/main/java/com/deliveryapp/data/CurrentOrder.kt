package com.deliveryapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class CurrentOrder (
    val title: String,
    val shortDescription: String,
    val mapAddress: String,
    val pointLat: Double,
    val pointLong: Double,
    val date: String,
    val money: Int,
    val phoneNumber: String? = null,
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
)

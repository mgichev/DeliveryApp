package com.deliveryapp.deliverymodule.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrentOrderDB (
    val title: String,
    val shortDescription: String,
    val fromMapAddress: String,
    val toMapAddress: String,
    val fromPointLat: Double,
    val fromPointLong: Double,
    val toPointLat: Double,
    val toPointLong: Double,
    val date: String,
    val money: Int,
    val phoneNumber: String? = null,
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
)

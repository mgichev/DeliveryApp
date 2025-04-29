package com.deliveryapp.deliverymodule.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FinishOrderDB (
    val title: String,
    val money: Int,
    val orderAddress: String,
    val isSuccessful: Boolean,
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
)
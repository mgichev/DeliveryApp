package com.deliveryapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CurrentOrder::class], version = 1)
abstract class CurrentOrdersDB : RoomDatabase() {
    abstract fun dao(): CurrentOrderDao
}
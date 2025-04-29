package com.deliveryapp.deliverymodule.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deliveryapp.deliverymodule.data.db.model.CurrentOrderDB
import com.deliveryapp.deliverymodule.data.db.model.FinishOrderDB

@Database(entities = [CurrentOrderDB::class, FinishOrderDB::class], version = 1)
abstract class OrdersDB : RoomDatabase() {
    abstract fun currentOrderDao(): CurrentOrderDao
    abstract fun finishOrderDao(): FinishOrderDao
}
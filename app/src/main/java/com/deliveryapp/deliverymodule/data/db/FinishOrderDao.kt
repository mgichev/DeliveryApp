package com.deliveryapp.deliverymodule.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deliveryapp.deliverymodule.data.db.model.FinishOrderDB
import kotlinx.coroutines.flow.Flow

@Dao
interface FinishOrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: FinishOrderDB)

    @Query("SELECT * FROM FinishOrderDB")
    fun fetchOrders(): Flow<List<FinishOrderDB>>

    @Query("DELETE FROM FinishOrderDB WHERE uid = :uid")
    suspend fun deleteOrder(uid: Int)
}
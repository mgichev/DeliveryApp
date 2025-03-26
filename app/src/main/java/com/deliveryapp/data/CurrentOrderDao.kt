package com.deliveryapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentOrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: CurrentOrder)

    @Query("SELECT * FROM CurrentOrder")
    fun fetchOrders(): Flow<List<CurrentOrder>>

    @Query("DELETE FROM CurrentOrder WHERE uid = :uid")
    suspend fun deleteOrder(uid: Int)
}
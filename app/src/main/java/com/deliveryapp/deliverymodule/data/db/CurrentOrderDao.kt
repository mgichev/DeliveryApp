package com.deliveryapp.deliverymodule.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deliveryapp.deliverymodule.data.db.model.CurrentOrderDB
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentOrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: CurrentOrderDB)

    @Query("SELECT * FROM CurrentOrderDB")
    fun fetchOrders(): Flow<List<CurrentOrderDB>>

    @Query("DELETE FROM CurrentOrderDB WHERE uid = :uid")
    suspend fun deleteOrder(uid: Int)
}
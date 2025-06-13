// Автор: Гичев М. А., КТбо4-8
// Тема: ВКР. Разработка мобильного приложения для работы курьера
// Описание: Интерфейс для предоставления функций работы с БД

package com.deliveryapp.deliverymodule.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deliveryapp.deliverymodule.data.db.model.CurrentOrderDB
import kotlinx.coroutines.flow.Flow

/**
 * Интерфейс, необходимый для работы библиотеки Room, для БД с текущими заказами.
 * Предоставляет методы для вставки, получения и удаления заказов.
 */
@Dao
interface CurrentOrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: CurrentOrderDB)

    @Query("SELECT * FROM CurrentOrderDB")
    fun fetchOrders(): Flow<List<CurrentOrderDB>>

    @Query("DELETE FROM CurrentOrderDB WHERE uid = :uid")
    suspend fun deleteOrder(uid: Int)
}
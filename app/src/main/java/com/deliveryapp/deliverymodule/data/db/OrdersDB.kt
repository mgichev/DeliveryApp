// Автор: Гичев М. А., КТбо4-8
// Тема: ВКР. Разработка мобильного приложения для работы курьера
// Описание: Интерфейс для предоставления функций работы с БД

package com.deliveryapp.deliverymodule.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deliveryapp.deliverymodule.data.db.model.CurrentOrderDB
import com.deliveryapp.deliverymodule.data.db.model.FinishOrderDB

/**
 * Абстрактный класс БД Room для хранения текущих и завершенных заказов.
 * Определяет структуру БД и предоставляет доступ к DAO-интерфейсам.
 *
 * @property entities Список классов сущностей, которые будут храниться в базе данных
 * [CurrentOrderDB] - текущие активные заказы
 * [FinishOrderDB] - завершенные/выполненные заказы
 * @property version Текущая версия БД
 */
@Database(entities = [CurrentOrderDB::class, FinishOrderDB::class], version = 1)
abstract class OrdersDB : RoomDatabase() {
    abstract fun currentOrderDao(): CurrentOrderDao
    abstract fun finishOrderDao(): FinishOrderDao
}
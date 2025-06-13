// Автор: Гичев М. А., КТбо4-8
// Тема: ВКР. Разработка мобильного приложения для работы курьера
// Описание: Класс для хранения завершенных заказов в БД

package com.deliveryapp.deliverymodule.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Сущность базы данных для хранения информации о завершенных заказах.
 * Представляет заказ, который был завершен курьером.
 *
 * @property title Название заказа
 * @property shortDescription Краткое описание заказа
 * @property fromMapAddress Адрес отправления
 * @property toMapAddress Адрес назначения
 * @property fromPointLat Широта точки отправления
 * @property fromPointLong Долгота точки отправления
 * @property toPointLat Широта точки назначения
 * @property toPointLong Долгота точки назначения
 * @property date Дата выполнения заказа
 * @property money Оплата
 * @property phoneNumber Номер телефона
 * @property uid Уникальный идентификатор заказа в БД
 */
@Entity
data class FinishOrderDB(
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
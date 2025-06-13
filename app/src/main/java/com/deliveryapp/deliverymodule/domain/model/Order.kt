// Автор: Гичев М. А., КТбо4-8
// Тема: ВКР. Разработка мобильного приложения для работы курьера
// Описание: Список данных текущих и новых заказов

package com.deliveryapp.deliverymodule.domain.model

import com.yandex.mapkit.geometry.Point

/**
 * Модель данных заказа на доставку.
 * Содержит полную информацию о заказе, включая точки маршрута, оплату и контактные данные.
 *
 * @property title Название заказа
 * @property shortDescription Краткое описание груза и особых условий доставки
 * @property fromMapAddress Адрес точки отправления в текстовом формате
 * @property toMapAddress Адрес точки назначения в текстовом формате
 * @property fromPoint Географические координаты точки отправления (широта/долгота)
 * @property toPoint Географические координаты точки назначения (широта/долгота)
 * @property date Дата и время доставки в строковом формате
 * @property money Сумма вознаграждения курьеру
 * @property distance Расстояние между точками в километрах
 * @property uid Уникальный идентификатор заказа
 * @property phoneNumber Номер телефона
 */
data class Order (
    val title: String,
    val shortDescription: String,
    val fromMapAddress: String,
    val toMapAddress: String,
    val fromPoint: Point,
    val toPoint: Point,
    val date: String,
    val money: Int,
    val distance: Float,
    val uid: Int? = null,
    val phoneNumber: String? = null,
)
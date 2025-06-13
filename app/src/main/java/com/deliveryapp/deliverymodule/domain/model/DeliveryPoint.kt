// Автор: Гичев М. А., КТбо4-8
// Тема: ВКР. Разработка мобильного приложения для работы курьера
// Описание: Список доступных точек доставки и модель точки доставки

package com.deliveryapp.deliverymodule.domain.model

import com.example.deliveryapp.R
import com.yandex.mapkit.geometry.Point

/**
 * Точка доставки, содержащая географические координаты и тип точки.
 * Используется для маршрутизации и визуализации на карте.
 *
 * @property point Географические координаты точки (широта/долгота)
 * @property deliveryPointTypes Тип точки доставки, определяющий её назначение и иконку
 */
class DeliveryPoint(
    val point: Point,
    val deliveryPointTypes: DeliveryPointTypes,
)

/**
 * Типы точек доставки с соответствующими иконками.
 * Определяет визуальное отображение и логическое назначение точки.
 *
 * @property imageDrawable Ресурс иконки для отображения на карте
 */
enum class DeliveryPointTypes(val imageDrawable: Int) {
    FROM_DELIVERY(R.drawable.box_24),
    TO_ORDER(R.drawable.delivery_metka_100),
    PERSON(R.drawable.start)
}
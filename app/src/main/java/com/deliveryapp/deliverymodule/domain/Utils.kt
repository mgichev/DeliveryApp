// Автор: Гичев М. А., КТбо4-8
// Тема: ВКР. Разработка мобильного приложения для работы курьера
// Описание: Вспомогательные функции для решения задач, несвязанных с UI

package com.deliveryapp.deliverymodule.domain

import com.deliveryapp.App
import com.yandex.mapkit.geometry.Point

/**
 * Генерирует URL для статической карты Yandex с маркерами двух точек.
 *
 * @param point1 Первая точка на карте (начальная точка маршрута)
 * @param point2 Вторая точка на карте (конечная точка маршрута)
 * @return URL строку для загрузки статического изображения карты с маркерами
 */

fun createGlideLink(point1: Point, point2: Point): String {
    val api = App.STATIC_MAP_API
    val template =
        "https://static-maps.yandex.ru/v1?&pt=${point1.longitude},${point1.latitude},round1~${point2.longitude},${point2.latitude},round2&size=300,300&apikey=$api"
    return template
}
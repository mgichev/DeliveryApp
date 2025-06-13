// Автор: Гичев М. А., КТбо4-8
// Тема: ВКР. Разработка мобильного приложения для работы курьера
// Описание: UI-класс для управления логикой отображения карты

package com.deliveryapp.deliverymodule.ui.map

import android.content.Context
import com.deliveryapp.deliverymodule.domain.model.DeliveryPoint
import com.deliveryapp.deliverymodule.domain.model.DeliveryPointTypes
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView


/**
 * Менеджер для управления отображением карты и маршрутов доставки.
 * Обеспечивает взаимодействие с Yandex MapKit для отображения точек доставки,
 * управления положением камеры и построения маршрутов.
 *
 * @property mapView View-компонент карты для отображения
 * @property context Контекст приложения для доступа к ресурсам
 */
class MapManager(
    private val mapView: MapView,
    private val context: Context
) {

    private val routesManager = RoutesManager(mapView, context)

    init {
        moveToStartPoint()
    }

    /**
     * Устанавливает точки на карте и строит маршрут между ними.
     * Первая точка в списке становится центром карты.
     *
     * @param list Список пар (точка, тип точки) для отображения
     */
    fun setMapPoints(list: List<Pair<Point, DeliveryPointTypes>>) {
        if (list.isNotEmpty()) moveToPoint(list[0].first)
        routesManager.setNewRoute(list.map { it -> DeliveryPoint(it.first, it.second) })
    }

    /**
     * Перемещает камеру карты к стартовой точке по умолчанию.
     * Используется при инициализации карты.
     */
    private fun moveToStartPoint() {
        mapView.mapWindow.map.move(CameraPosition(START_POINT, ZOOM, 0f, 0f))
    }

    /**
     * Перемещает камеру карты к указанной точке с увеличенным zoom.
     * Также устанавливает эту точку как стартовую для маршрута.
     *
     * @param point Точка, к которой нужно переместить камеру
     */
    fun moveToPoint(point: Point) {
        mapView.mapWindow.map.move(CameraPosition(point, ZOOM_BIG, 0f, 0f))
        routesManager.addStartPoint(point)
    }

    companion object {
        private val START_POINT = Point(47.217310, 38.899480)
        private const val ZOOM = 12f
        private const val ZOOM_BIG = 15f
        private val objectList = listOf(
            DeliveryPoint(Point(47.218663, 38.909763), DeliveryPointTypes.FROM_DELIVERY),
            DeliveryPoint(Point(47.212469, 38.912593), DeliveryPointTypes.FROM_DELIVERY),
            DeliveryPoint(Point(47.210100, 38.916141), DeliveryPointTypes.FROM_DELIVERY)
        )
    }

}


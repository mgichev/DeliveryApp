package com.deliveryapp.deliverymodule.ui.map

import android.content.Context
import android.widget.Toast
import com.deliveryapp.deliverymodule.domain.model.DeliveryPoint
import com.deliveryapp.deliverymodule.domain.model.DeliveryPointTypes
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider


class MapManager(
    private val mapView: MapView,
    private val context: Context
) {

    private val objectsToVisitList = objectList
    private val routesManager = RoutesManager(mapView, context)

    private val placemarks = mutableListOf<PlacemarkMapObject>()

    init {
        moveToStartPoint()
    }

    fun addPoint(point: Point) {
        routesManager.addPoint(point)
    }

    fun setMapPoints(list: List<Pair<Point, DeliveryPointTypes>>) {
        if (list.isNotEmpty()) moveToPoint(list[0].first)
        routesManager.setNewRoute(list.map { it -> DeliveryPoint(it.first,it.second) })
    }

    private fun moveToStartPoint() {
        mapView.mapWindow.map.move(CameraPosition(START_POINT, ZOOM, 0f, 0f))
    }

    fun moveToPoint(point: Point) {
        mapView.mapWindow.map.move(CameraPosition(point, ZOOM_BIG, 0f, 0f))
        routesManager.addStartPoint(point)
    }

    fun drawRoute(
        destinations: List<DeliveryPoint>,
        pointsToVisit: List<DeliveryPoint>? = null,
        isByStep: Boolean = false
    ) {
        if (destinations.size < 2)
            throw IllegalArgumentException("Number of destinations must me more than 2!")

        if (!isByStep) {
            routesManager.setNewRoute(destinations)
        }
    }

    private fun placeObjectsToMap() {
        with(mapView.mapWindow.map.mapObjects) {
            for (item in objectsToVisitList) {
                placemarks.add(addPlacemark().apply {
                    geometry = item.point
                    setIcon(ImageProvider.fromResource(
                        context, item.deliveryPointTypes.imageDrawable))
                })
            }
        }
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


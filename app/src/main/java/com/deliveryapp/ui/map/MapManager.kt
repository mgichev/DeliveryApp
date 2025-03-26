package com.deliveryapp.ui.map

import android.content.Context
import android.widget.Toast
import com.deliveryapp.domain.DeliveryPoint
import com.deliveryapp.domain.DeliveryPointTypes
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
//        addPoint()
//        addPoint()
//        addPoint()
//        addPoint()
//        addPoint()
        //placeObjectsToMap()

//        addPoint()
//        addPoint()
    }

    fun addPoint(point: Point) {
        routesManager.addPoint(point)
    }

    private fun moveToStartPoint() {
        mapView.mapWindow.map.move(CameraPosition(START_POINT, ZOOM, 0f, 0f))
    }

    fun drawNextRoute() {
        if (!routesManager.isDrawByStepMode)
            routesManager.startDrawByStep()
        try {
            routesManager.drawNextRoutePart()
        } catch (e: IllegalArgumentException) {
            Toast.makeText(context, "${e.message}", Toast.LENGTH_LONG).show()
        }
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
        private val objectList = listOf(
            DeliveryPoint(Point(47.218663, 38.909763), DeliveryPointTypes.DEFAULT),
            DeliveryPoint(Point(47.212469, 38.912593), DeliveryPointTypes.DEFAULT),
            DeliveryPoint(Point(47.210100, 38.916141), DeliveryPointTypes.DEFAULT)
        )
    }

}


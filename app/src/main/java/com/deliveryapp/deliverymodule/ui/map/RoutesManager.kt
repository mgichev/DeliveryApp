package com.deliveryapp.deliverymodule.ui.map

import android.content.Context
import android.graphics.Color
import android.widget.Toast
import com.deliveryapp.deliverymodule.domain.model.DeliveryPoint
import com.deliveryapp.deliverymodule.domain.model.DeliveryPointTypes
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
import com.yandex.mapkit.directions.DirectionsFactory
import com.yandex.mapkit.directions.driving.DrivingOptions
import com.yandex.mapkit.directions.driving.DrivingRoute
import com.yandex.mapkit.directions.driving.DrivingRouterType
import com.yandex.mapkit.directions.driving.DrivingSession
import com.yandex.mapkit.directions.driving.DrivingSession.DrivingRouteListener
import com.yandex.mapkit.directions.driving.VehicleOptions
import com.yandex.mapkit.directions.driving.VehicleType
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.PolylineMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.Error
import com.yandex.runtime.image.ImageProvider
import com.yandex.runtime.network.NetworkError

class RoutesManager(mapView: MapView, private val context: Context) {

    private var drivingSession: DrivingSession? = null

    private var routesCollection = mapView.mapWindow.map.mapObjects.addCollection()
    private var placemarksCollection = mapView.mapWindow.map.mapObjects.addCollection()

    private var routePoints = emptyList<DeliveryPoint>()
        set(value) {
            field = value
            onRoutePointsUpdated()
        }

    private var fullRoutePoints = emptyList<DeliveryPoint>()


    private var lastDrawRouteID = 0

    private var routes = emptyList<DrivingRoute>()
        set(value) {
            field = value
            onRoutesChanged()
        }

    var isDrawByStepMode = false
        private set

    private fun onRoutesChanged() {
        routesCollection.clear()
        if (routes.isEmpty()) return

        routes.forEachIndexed { index, route ->
            routesCollection.addPolyline(route.geometry).apply {
                if (index == 0) styleMainRoute() else styleAlternativeRoute()
            }
        }
    }

    fun setNewRoute(points: List<DeliveryPoint>) {
        routePoints = points
    }

    fun drawNextRoutePart() {
        if (fullRoutePoints.getOrNull(lastDrawRouteID + 1) == null)
            throw IllegalArgumentException("No such route id: ${lastDrawRouteID + 1}")
        else {
            routePoints = listOf(fullRoutePoints[lastDrawRouteID],
                fullRoutePoints[lastDrawRouteID + 1])
            lastDrawRouteID++
        }
    }

    fun redrawFullRoute() {
        if (routePoints.isEmpty())
            throw IllegalStateException("Route points list is empty")
        routePoints.toMutableList()
        isDrawByStepMode = false
        routePoints = fullRoutePoints.toMutableList()
    }

    fun startDrawByStep() {
        if (isDrawByStepMode)
            throw IllegalStateException("Step drawing mode is already work")
        lastDrawRouteID = 0
        isDrawByStepMode = true
        fullRoutePoints = routePoints.toMutableList()
    }

    private val drivingRouteListener = object : DrivingRouteListener {
        override fun onDrivingRoutes(drivingRoutes: MutableList<DrivingRoute>) {
            routes = drivingRoutes
        }

        override fun onDrivingRoutesError(error: Error) {
            when(error) {
                is NetworkError -> Toast.makeText(context, "Error network",
                    Toast.LENGTH_LONG).show()
                else -> Toast.makeText(context, "Other error",
                    Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun onRoutePointsUpdated() {
        placemarksCollection.clear()

        if (routePoints.isEmpty()) {
            drivingSession?.cancel()
            routes = emptyList()
            return
        }

        routePoints.forEach {
            placemarksCollection.addPlacemark().apply {
                geometry = it.point
                setIcon(ImageProvider.fromResource(context, it.deliveryPointTypes.imageDrawable))
            }
        }

        if (routePoints.size < 2) return

        val drivingRouter = DirectionsFactory.getInstance().createDrivingRouter(DrivingRouterType.COMBINED)
        val drivingOptions = DrivingOptions()
        val vehicleOptions = VehicleOptions()
        vehicleOptions.vehicleType = VehicleType.DEFAULT
        val points = buildList {
            for (point in routePoints) {
                add(
                    RequestPoint(
                    point.point,
                    RequestPointType.WAYPOINT,
                    null,
                    null,
                    null)
                )
            }
        }

        drivingSession = drivingRouter.requestRoutes(
            points,
            drivingOptions,
            vehicleOptions,
            drivingRouteListener
        )
    }

    fun addPoint(point: Point) {
        if (isDrawByStepMode)
            throw IllegalStateException("Cant add point when Step Drawing Mode Working!")
        routePoints = routePoints + DeliveryPoint(point,
            DeliveryPointTypes.FROM_DELIVERY)
    }

    fun generateRandomPoint(): Point {
        val diffMinLat = 47.195480
        val diffMinLong = 38.811795

        val diffMaxLat = 47.278133
        val diffMaxLong = 38.937549
        val lat = diffMinLat + Math.random() * (diffMaxLat - diffMinLat)
        val long = diffMinLong + Math.random() * (diffMaxLong - diffMinLong)
        return Point(lat, long)

    }


    private fun PolylineMapObject.styleMainRoute() {
        zIndex = 10f
        setStrokeColor(Color.GRAY)
        style = style.apply {
            strokeWidth = 5f
            outlineColor = Color.BLACK
            outlineWidth = 3f
        }
    }

    private fun PolylineMapObject.styleAlternativeRoute() {
        zIndex = 5f
        setStrokeColor(Color.GREEN)
        style = style.apply {
            strokeWidth = 4f
            outlineColor = Color.BLACK
            outlineWidth = 2f
        }
    }
}
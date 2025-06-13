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

    private var routes = emptyList<DrivingRoute>()
        set(value) {
            field = value
            onRoutesChanged()
        }

    private fun onRoutesChanged() {
        routesCollection.clear()
        if (routes.isEmpty()) return

        routesCollection.addPolyline(routes[0].geometry).apply {
            styleMainRoute()
        }

//        routes.forEachIndexed { index, route ->
//            routesCollection.addPolyline(route.geometry).apply {
//                if (index == 0) styleMainRoute()
//            }
//        }
    }

    fun setNewRoute(points: List<DeliveryPoint>) {
        routePoints = points
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
            routesCollection.clear()
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
//        val points = buildList {
//            for (point in routePoints) {
//                add(
//                    RequestPoint(
//                    point.point,
//                    RequestPointType.WAYPOINT,
//                    null,
//                    null,
//                    null)
//                )
//            }
//        }


        val points = buildList {
            routePoints.forEachIndexed { index, point ->
                val type = when (index) {
                    0 -> RequestPointType.WAYPOINT      // Старт
                    routePoints.lastIndex -> RequestPointType.WAYPOINT // Финиш
                    else -> RequestPointType.VIAPOINT   // Промежуточные
                }

                add(
                    RequestPoint(
                        point.point,
                        type,
                        null,
                        null,
                        null
                    )
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

    fun addStartPoint(point: Point) {
        routePoints = listOf(DeliveryPoint(point, DeliveryPointTypes.PERSON))  + routePoints
    }

    fun addPoint(point: Point) {
        routePoints = routePoints + DeliveryPoint(point,
            DeliveryPointTypes.FROM_DELIVERY)
    }

    private fun PolylineMapObject.styleMainRoute() {
        zIndex = 10f
        setStrokeColor(Color.parseColor("#118CCA"))
        style = style.apply {
            strokeWidth = 4f
            outlineColor = Color.parseColor("#118CCA")
            outlineWidth = 1f
        }
    }

//    private fun PolylineMapObject.styleAlternativeRoute() {
//        zIndex = 5f
//        setStrokeColor(Color.GREEN)
//        style = style.apply {
//            strokeWidth = 3f
//            outlineColor = Color.BLACK
//            outlineWidth = 1f
//        }
//    }
}
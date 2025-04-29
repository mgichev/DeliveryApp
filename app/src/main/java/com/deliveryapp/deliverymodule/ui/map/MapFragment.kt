package com.deliveryapp.deliverymodule.ui.map

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.view.size
import androidx.fragment.app.Fragment
import com.deliveryapp.deliverymodule.domain.model.DeliveryPointTypes
import com.deliveryapp.deliverymodule.ui.orders.OrdersViewModel
import com.example.deliveryapp.R
import com.example.deliveryapp.databinding.FragmentMapBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.MapLoadStatistics
import com.yandex.mapkit.map.MapLoadedListener
import com.yandex.mapkit.mapview.MapView
import org.koin.androidx.viewmodel.ext.android.viewModel


class MapFragment : Fragment(), MapLoadedListener {

    private var _binding: FragmentMapBinding? = null
    private lateinit var mapManager: MapManager
    private val binding get() = _binding!!
    private lateinit var mapView: MapView
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val ordersViewModel: OrdersViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        MapKitFactory.initialize(this.context)
        mapView = binding.map
        mapView.mapWindow.map.setMapLoadedListener(this)
        mapManager = MapManager(mapView, requireActivity().applicationContext)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        MapKitFactory.getInstance().onStop()
        mapView.onStop()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.showMapSettingsBtn.setOnClickListener {
            showDialog()
        }

        binding.fab.setOnClickListener {
            getLocation()
        }

        ordersViewModel.ordersToShow.observe(this.viewLifecycleOwner) {
            val list = ordersViewModel.currentList.value
            val mapItems = mutableListOf<Pair<Point, DeliveryPointTypes>>()
            for (item in it.indices) {
                if (list != null && it[item]) {
                    mapItems.add(Pair(list[item].fromPoint, DeliveryPointTypes.FROM_DELIVERY))
                    mapItems.add(Pair(list[item].toPoint, DeliveryPointTypes.TO_ORDER))
                }
            }
            mapManager.setMapPoints(mapItems)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapLoaded(p0: MapLoadStatistics) {
        binding.map.visibility = View.VISIBLE
        binding.showMapSettingsBtn.visibility = View.VISIBLE
        binding.loadingTV.visibility = View.GONE
    }

    private fun showDialog() {

        var checkedItems = mutableListOf(false)
        ordersViewModel.ordersToShow.observe(viewLifecycleOwner) {
            checkedItems = mutableListOf(false, *it.toTypedArray())
        }

        var orders = mutableListOf("")
        ordersViewModel.currentList.observe(viewLifecycleOwner) {
            val list = it.map { item -> item.fromMapAddress }
            orders = mutableListOf("Show All", *list.toTypedArray())

        }


        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.select_orders_for_show)
            .setMultiChoiceItems(
                orders.toTypedArray(),
                checkedItems.toBooleanArray()
            ) { dialog, which, state ->
                Toast.makeText(requireContext(), which.toString(), Toast.LENGTH_LONG).show()
                if (which == 0) {
                    val list = (dialog as AlertDialog).listView
                    repeat(list.size) { id ->
                        list.setItemChecked(id, state)
                        checkedItems[id] = state
                    }
                } else {
                    checkedItems[which] = state
                }
            }
            .setPositiveButton(R.string.confirm) { _, _ ->
                val itemsToCheck = checkedItems.toMutableList()
                for (index in itemsToCheck.indices) {
                    if (index == 0)
                        continue
                    ordersViewModel.setOrdersToShow(index - 1, itemsToCheck[index])
                }
            }
            .setNegativeButton(R.string.undo) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(
                        requireActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        requireActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                fusedLocationProviderClient.getCurrentLocation(
                    LocationRequest.QUALITY_HIGH_ACCURACY,
                    object : CancellationToken() {
                        override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken = CancellationTokenSource().token

                        override fun isCancellationRequested() = false

                    })
                    .addOnSuccessListener { location: Location? ->
                        if (location == null) {
                            Toast.makeText(context, "Не удалось получить текущую локацию", Toast.LENGTH_LONG).show()
                        } else {
                            val lat = location.latitude
                            val long = location.longitude
                            mapManager.moveToPoint(Point(lat,long))
                        }
                    }

            } else {
                Toast.makeText(context, "Включите GPS и интернет", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermission()
        }
    }

    private fun checkPermissions(): Boolean {
        return (ActivityCompat.checkSelfPermission(
            requireActivity(),
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                && (ActivityCompat.checkSelfPermission(
            requireActivity(),
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED))
    }

    private fun isLocationEnabled() : Boolean {
        val locationManager: LocationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                getLocation()
        }
    }

    companion object {
        val LOCATION_PERMISSION_CODE = 101
    }


}
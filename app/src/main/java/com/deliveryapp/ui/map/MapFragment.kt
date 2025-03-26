package com.deliveryapp.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.deliveryapp.ui.orders.OrdersViewModel
import com.example.deliveryapp.databinding.FragmentMapBinding
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.map.MapLoadStatistics
import com.yandex.mapkit.map.MapLoadedListener
import com.yandex.mapkit.mapview.MapView
import org.koin.androidx.viewmodel.ext.android.viewModel


class MapFragment: Fragment(), MapLoadedListener {

    private var _binding: FragmentMapBinding? = null
    private lateinit var mapManager: MapManager
    private val binding get() = _binding!!
    private lateinit var mapView: MapView

    private val ordersViewModel: OrdersViewModel by viewModel()

    // private val booksViewModel: ShopBooksViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        MapKitFactory.initialize(this.context)
        mapView = binding.map
        mapView.mapWindow.map.setMapLoadedListener(this)
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

        mapManager = MapManager(mapView, requireActivity().applicationContext)

        ordersViewModel.currentList.observe(this.viewLifecycleOwner) {
            for (item in it) {
                mapManager.addPoint(item.point)
            }
        }

        binding.addPoint.setOnClickListener {
            //mapManager.addPoint()
        }

        binding.drawNextRoute.setOnClickListener {
            mapManager.drawNextRoute()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapLoaded(p0: MapLoadStatistics) {
        binding.map.visibility = View.VISIBLE
        binding.loadingTV.visibility = View.GONE
        binding.addPoint.visibility = View.VISIBLE
        binding.drawNextRoute.visibility = View.VISIBLE
        Toast.makeText(this.context, "Loaded", Toast.LENGTH_SHORT).show()
    }


}
package com.deliveryapp.ui.orders

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.deliveryapp.domain.Order
import com.deliveryapp.ui.orders.adapters.CurrentOrdersAdapter
import com.deliveryapp.ui.orders.adapters.NewOrdersAdapter
import com.example.deliveryapp.databinding.FragmentOrdersBinding
import com.google.android.material.tabs.TabLayout
import com.yandex.mapkit.geometry.Point
import org.koin.androidx.viewmodel.ext.android.viewModel


class OrdersFragment : Fragment(), ConfirmEventListener {

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    private lateinit var newOrdersAdapter: NewOrdersAdapter
    private lateinit var currentOrdersAdapter: CurrentOrdersAdapter

    private var newsOrderList = mutableListOf<Order>()
    private var currentOrderList = mutableListOf<Order>()
    private var finishOrderList = mutableListOf<Order>()

    init {
        finishOrderList = oldOrders.toMutableList()
    }

    private val ordersViewModel: OrdersViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setListOnSelectedTab(selectedTab: Int) {
        when (selectedTab) {
            NEW_ORDERS -> {
                binding.ordersRV.adapter = newOrdersAdapter
            }
            CURRENT_ORDERS -> {
                binding.ordersRV.adapter = currentOrdersAdapter
            }
            FINISHED_ORDERS -> newOrdersAdapter.ordersList = finishOrderList
            else -> Log.e("Wrong tab Exception", "This tab not found")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val selectedTab = tab?.position
                setListOnSelectedTab(selectedTab ?: -1)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.d("Tab", "Unselected ${tab?.id}")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

        val layoutManager = LinearLayoutManager(this.context)

        newOrdersAdapter = NewOrdersAdapter(this)
        currentOrdersAdapter = CurrentOrdersAdapter(this)

        newOrdersAdapter.ordersList = newsOrderList
        currentOrdersAdapter.ordersList = currentOrderList

        ordersViewModel.currentList.observe(this.viewLifecycleOwner) {
            currentOrdersAdapter.ordersList = it.toMutableList()
        }

        ordersViewModel.newOrderList.observe(this.viewLifecycleOwner) {
            newOrdersAdapter.ordersList = it.toMutableList()
        }

        setListOnSelectedTab(binding.tab.selectedTabPosition)

        binding.ordersRV.adapter = newOrdersAdapter
        binding.ordersRV.layoutManager = layoutManager
    }

//    private fun getAddressByPoint(point: Point) : String {
//
//        val zoomLevel = 16
//        var address = ""
//        session = searchManager.submit(
//            point,
//            zoomLevel,
//            SearchOptions(),
//            object : SearchListener {
//                override fun onSearchResponse(request: Response) {
//                    address = request
//                        .collection
//                        .children[0]
//                        .obj
//                        ?.metadataContainer
//                        ?.getItem(ToponymObjectMetadata::class.java)
//                        ?.address
//                        ?.formattedAddress ?: "Such address not found"
//                }
//
//                override fun onSearchError(p0: Error) {
//                    Log.d("Error", "Search error")
//                }
//
//            }
//        )
//
//        return address
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val NEW_ORDERS = 0
        const val CURRENT_ORDERS = 1
        const val FINISHED_ORDERS = 2


        val oldOrders = listOf(
            Order("Order New order", "Short descr", "Moscow", Point(47.218663, 38.909763),"2024", 10000),
            Order("New order1", "Short descr", "Moscow", Point(47.218663, 38.909763),"2024", 10000),
            Order("New order2", "Short descr", "Moscow", Point(47.218663, 38.909763), "2024", 10000),
        )
    }

    override fun onGetOrderBtnClicked(order: Order) {
        ordersViewModel.insertOrder(order)
    }

    override fun onFinishOrderBtnClicked(order: Order) {
        order.uid?.let {
            ordersViewModel.deleteOrder(order.uid)
        }
    }
}
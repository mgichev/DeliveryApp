// Автор: Гичев М. А., КТбо4-8
// Тема: ВКР. Разработка мобильного приложения для работы курьера
// Описание: страница заказов с подразделами новых, текущих, завершенных заказов

package com.deliveryapp.deliverymodule.ui.orders

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.deliveryapp.deliverymodule.domain.model.Order
import com.deliveryapp.deliverymodule.ui.orders.adapters.CurrentOrdersAdapter
import com.deliveryapp.deliverymodule.ui.orders.adapters.FinishOrdersAdapter
import com.deliveryapp.deliverymodule.ui.orders.adapters.NewOrdersAdapter
import com.example.deliveryapp.databinding.FragmentOrdersBinding
import com.google.android.material.tabs.TabLayout
import com.yandex.mapkit.geometry.Point
import org.koin.androidx.viewmodel.ext.android.viewModel


class OrdersFragment : Fragment(), ConfirmEventListener {

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    private lateinit var newOrdersAdapter: NewOrdersAdapter
    private lateinit var currentOrdersAdapter: CurrentOrdersAdapter
    private lateinit var finishOrdersAdapter: FinishOrdersAdapter

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

    /**
     * Выбор адаптера заказов для отображения на экране в зависимости от selectedTab.
     * @param selectedTab выбранный подраздел заказов
     */
    private fun setListOnSelectedTab(selectedTab: Int) {
        when (selectedTab) {
            NEW_ORDERS -> {
                binding.ordersRV.adapter = newOrdersAdapter
            }

            CURRENT_ORDERS -> {
                binding.ordersRV.adapter = currentOrdersAdapter
            }

            FINISHED_ORDERS -> binding.ordersRV.adapter = finishOrdersAdapter
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

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

        val layoutManager = LinearLayoutManager(this.context)

        newOrdersAdapter = NewOrdersAdapter(this)
        currentOrdersAdapter = CurrentOrdersAdapter(this)
        finishOrdersAdapter = FinishOrdersAdapter(this)

        newOrdersAdapter.ordersList = newsOrderList
        currentOrdersAdapter.ordersList = currentOrderList
        finishOrdersAdapter.ordersList = finishOrderList

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val NEW_ORDERS = 0
        const val CURRENT_ORDERS = 1
        const val FINISHED_ORDERS = 2


        val oldOrders = listOf(
            Order(
                "Order New order",
                "Short descr",
                "Moscow",
                "Moscow",
                Point(47.218663, 38.909763),
                Point(47.23692, 38.89323),
                "2024",
                10000,
                0f
            ),
            Order(
                "New order1",
                "Short descr",
                "Moscow",
                "Riga",
                Point(47.218663, 38.909763),
                Point(47.23918503818342, 38.9132146996677),
                "2024",
                10000,
                0f
            ),
            Order(
                "New order2",
                "Short descr",
                "Moscow",
                "Riga",
                Point(47.218663, 38.909763),
                Point(47.2137459607235, 38.8939744174389),
                "2024",
                10000,
                0f
            ),
        )
    }

    override fun onGetOrderBtnClicked(order: Order) {
        ordersViewModel.insertCurrentOrder(order)
    }

    override fun onFinishOrderBtnClicked(order: Order) {
        order.uid?.let {
            ordersViewModel.deleteCurrentOrder(order.uid)
        }
    }


}
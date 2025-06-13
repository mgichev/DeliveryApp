package com.deliveryapp

import androidx.lifecycle.MutableLiveData
import com.deliveryapp.deliverymodule.data.OrderRepositoryImpl
import com.deliveryapp.deliverymodule.domain.model.FinishOrder
import com.deliveryapp.deliverymodule.domain.model.Order
import com.deliveryapp.deliverymodule.ui.orders.OrdersViewModel
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
@OptIn(ExperimentalCoroutinesApi::class)
class OrdersViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var orderRepository: OrderRepositoryImpl
    private lateinit var viewModel: OrdersViewModel

    private val sampleFromPoint = Point(55.75, 37.62)
    private val sampleToPoint = Point(55.76, 37.64)

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        orderRepository = mock()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init loads orders correctly`() = runTest {
        val newOrders = listOf(
            Order(
                title = "Order 1",
                shortDescription = "Desc 1",
                fromMapAddress = "From 1",
                toMapAddress = "To 1",
                fromPoint = sampleFromPoint,
                toPoint = sampleToPoint,
                date = "2025-05-25",
                money = 100,
                distance = 10f,
                uid = 1,
                phoneNumber = "1234567890"
            )
        )
        val currentOrders = listOf(
            Order(
                title = "Order 2",
                shortDescription = "Desc 2",
                fromMapAddress = "From 2",
                toMapAddress = "To 2",
                fromPoint = sampleFromPoint,
                toPoint = sampleToPoint,
                date = "2025-05-26",
                money = 200,
                distance = 20f,
                uid = 2,
                phoneNumber = "0987654321"
            )
        )
        val finishOrders = listOf(
            FinishOrder(
                title = "Finish 1",
                money = 150,
                orderAddress = "Address 1",
                isSuccessful = true
            )
        )

        whenever(orderRepository.fetchNewOrders()).thenReturn(newOrders)
        whenever(orderRepository.fetchCurrentOrders()).thenReturn(flowOf(currentOrders))
        whenever(orderRepository.fetchFinishOrders()).thenReturn(flowOf(finishOrders))

        viewModel = OrdersViewModel(orderRepository)

        // Запускаем корутины
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(newOrders, viewModel.newOrderList.value)
        assertEquals(currentOrders, viewModel.currentList.value)
        assertEquals(finishOrders, viewModel.finishList.value)
    }

    @Test
    fun `insertCurrentOrder calls repository`() = runTest {
        viewModel = OrdersViewModel(orderRepository)
        val order = Order(
            title = "Order Insert",
            shortDescription = "Desc",
            fromMapAddress = "From",
            toMapAddress = "To",
            fromPoint = sampleFromPoint,
            toPoint = sampleToPoint,
            date = "2025-05-27",
            money = 300,
            distance = 30f
        )

        viewModel.insertCurrentOrder(order)
        advanceUntilIdle()

        verify(orderRepository).insertCurrentOrder(order)
    }

    @Test
    fun `deleteCurrentOrder calls repository`() = runTest {
        viewModel = OrdersViewModel(orderRepository)
        val orderId = 42

        viewModel.deleteCurrentOrder(orderId)
        advanceUntilIdle()

        verify(orderRepository).deleteCurrentOrder(orderId)
    }

    @Test
    fun `insertFinishOrder calls repository`() = runTest {
        viewModel = OrdersViewModel(orderRepository)
        val order = Order(
            title = "Order Finish",
            shortDescription = "Desc",
            fromMapAddress = "From",
            toMapAddress = "To",
            fromPoint = sampleFromPoint,
            toPoint = sampleToPoint,
            date = "2025-05-28",
            money = 400,
            distance = 40f
        )

        viewModel.insertFinishOrder(order)
        advanceUntilIdle()

        verify(orderRepository).insertFinishOrder(order, true)
    }

    @Test
    fun `setOrdersToShow updates LiveData correctly`() = runTest {
        viewModel = OrdersViewModel(orderRepository)

        // Имитируем начальное значение ordersToShow
        val initialList = listOf(false, false, false)

        // Принудительно установим значение для проверки
        val liveDataField = OrdersViewModel::class.java.getDeclaredField("_ordersToShow")
        liveDataField.isAccessible = true
        liveDataField.set(viewModel, MutableLiveData(initialList))

        viewModel.setOrdersToShow(1, true)
        val updated = viewModel.ordersToShow.value

        assertEquals(listOf(false, true, false), updated)
    }
}

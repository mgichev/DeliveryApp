package com.deliveryapp

import com.deliveryapp.deliverymodule.data.OrderRepositoryImpl
import com.deliveryapp.deliverymodule.data.db.CurrentOrderDao
import com.deliveryapp.deliverymodule.data.db.FinishOrderDao
import com.deliveryapp.deliverymodule.domain.model.Order
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Юнит-тесты для класса [OrderRepositoryImpl], реализующего бизнес-логику заказов.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
@ExperimentalCoroutinesApi
class OrderRepositoryImplTest {

    private lateinit var currentOrderDao: CurrentOrderDao
    private lateinit var finishOrderDao: FinishOrderDao
    private lateinit var repository: OrderRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    /**
     * Подготовка окружения теста, инициализация DAO и репозитория, установка диспетчера.
     */
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        currentOrderDao = mock()
        finishOrderDao = mock()
        repository = OrderRepositoryImpl(currentOrderDao, finishOrderDao)
    }

    /**
     * Очистка диспетчера после тестов.
     */
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * Проверяет, что метод fetchNewOrders возвращает от 3 до 10 заказов с непустыми названиями.
     */
    @Test
    fun `fetchNewOrders return random number of orders`() {
        val result = repository.fetchNewOrders()
        assertTrue(result.size in 3..10)
        assertTrue(result.all { it.title.isNotBlank() })
    }

    /**
     * Проверяет, что insertCurrentOrder преобразует и сохраняет заказ через DAO.
     */
    @Test
    fun `insertCurrentOrder map and save order`() = runTest {
        val order = createFakeOrder()
        repository.insertCurrentOrder(order)
        verify(currentOrderDao).insert(any())
    }

    /**
     * Проверяет, что insertFinishOrder сохраняет завершённый заказ через DAO.
     */
    @Test
    fun `insertFinishOrder map and save finish order`() = runTest {
        val order = createFakeOrder()
        repository.insertFinishOrder(order, true)
        verify(finishOrderDao).insert(any())
    }

    /**
     * Проверяет, что deleteCurrentOrder вызывает удаление в currentOrderDao.
     */
    @Test
    fun `deleteCurrentOrder call dao`() = runTest {
        repository.deleteCurrentOrder(123)
        verify(currentOrderDao).deleteOrder(123)
    }

    /**
     * Проверяет, что метод calculateDistance возвращает положительное значение.
     */
    @Test
    fun `calculateDistance returns positive float`() {
        val p1 = Point(47.22663, 38.89492)
        val p2 = Point(47.227, 38.9)

        val result = OrderRepositoryImpl.calculateDistance(p1, p2)

        assertTrue(result > 0f)
    }

    /**
     * Создаёт тестовый объект заказа для использования в тестах.
     */
    private fun createFakeOrder(): Order {
        return Order(
            title = "Test",
            shortDescription = "Short",
            fromMapAddress = "From",
            toMapAddress = "To",
            fromPoint = Point(47.2, 38.9),
            toPoint = Point(47.3, 38.95),
            distance = 5.5f,
            date = "20-01-2025",
            money = 3000,
            phoneNumber = "+7-999-111-2222",
            uid = 123
        )
    }
}

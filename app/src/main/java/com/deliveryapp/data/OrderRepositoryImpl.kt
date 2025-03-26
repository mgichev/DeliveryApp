package com.deliveryapp.data

import com.deliveryapp.domain.Order
import com.deliveryapp.domain.OrderRepository
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OrderRepositoryImpl(private val currentOrderDao: CurrentOrderDao) : OrderRepository {

    override fun fetchNewOrders(): List<Order> {
        val list = mutableListOf<Order>()
        val number = (3..10).random()
        for (i in 1..number) {
            list.add(getOrder())
        }
        return list
    }

    override fun fetchCurrentOrders(): Flow<List<Order>> {
        return currentOrderDao.fetchOrders().map { it ->
            it.map { item ->
                Order(
                    title = item.title,
                    shortDescription = item.shortDescription,
                    mapAddress = item.mapAddress,
                    point = Point(item.pointLat, item.pointLong),
                    date = item.date,
                    money = item.money,
                    phoneNumber = item.phoneNumber,
                    uid = item.uid
                )
            }
        }
    }

    override suspend fun insertCurrentOrder(order: Order) {
        val currentOrder = CurrentOrder(
            title = order.title,
            shortDescription = order.shortDescription,
            mapAddress = order.mapAddress,
            pointLat = order.point.latitude,
            pointLong = order.point.longitude,
            date = order.date,
            money = order.money,
            phoneNumber = order.phoneNumber,
        )
        currentOrderDao.insert(currentOrder)
    }

    override suspend fun deleteOrder(uid: Int) {
        currentOrderDao.deleteOrder(uid)
    }


    private fun getOrder() : Order {
        return Order(
            title = titles.random(),
            shortDescription = shortDescription.random(),
            mapAddress = address.random(),
            point = point.random(),
            date = "18-01-2024, 12:00",
            3000,
            null,
            "+7-999-111-3333"
        )
    }

    companion object {
        val address = listOf("Moscow", "Taganrog", "Rome", "Peter", "Saransk", "Saratov", "Riga")

        val titles = listOf(
            "Доставка шкафа из магазина Сом",
            "Доставка обедов в офис",
            "Перевозка продуктов в магазин"
        )

        val shortDescription = listOf(
            "Необходимо доставить шкаф из магазина Сом на четвертый этаж дома. Грузчик будет ждать на месте",
            "Необходимо доставить еду в офис. Пожалуйста используйте термосумки",
            "Необходимо доставить 30 кг сгущенки из склада в магазин"
        )

        val point = listOf(
            Point(47.214186, 38.920955),
            Point(47.212071, 38.920023),
            Point(47.211080, 38.927788),
        )

    }
}
// Автор: Гичев М. А., КТбо4-8
// Тема: ВКР. Разработка мобильного приложения для работы курьера
// Описание: Репозиторий для работы с заказами через БД и удаленный сервер


package com.deliveryapp.deliverymodule.data

import android.location.Location
import com.deliveryapp.deliverymodule.data.db.model.CurrentOrderDB
import com.deliveryapp.deliverymodule.data.db.CurrentOrderDao
import com.deliveryapp.deliverymodule.data.db.model.FinishOrderDB
import com.deliveryapp.deliverymodule.data.db.FinishOrderDao
import com.deliveryapp.deliverymodule.domain.model.Order
import com.deliveryapp.deliverymodule.domain.repository.OrderRepository
import com.deliveryapp.deliverymodule.domain.model.FinishOrder
import com.google.firebase.firestore.FirebaseFirestore
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

/**
 * Реализация репозитория для работы с заказами.
 * Обеспечивает взаимодействие с Firestore (новые заказы) и локальной БД (текущие/завершенные заказы).
 */
class OrderRepositoryImpl(
    private val currentOrderDao: CurrentOrderDao, private val finishOrderDao: FinishOrderDao,
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : OrderRepository {


    /**
     * Получает новые заказы из Firestore.
     * @return Список новых заказов или пустой список при ошибке
     */
    override suspend fun fetchNewOrders(): List<Order> {
        return try {
            val querySnapshot = firestore
                .collection("orders")
                .get()
                .await()

            querySnapshot.documents.mapNotNull { document ->
                try {
                    val fromGeoPoint = document.getGeoPoint("fromPoint")
                    val toGeoPoint = document.getGeoPoint("toPoint")

                    Order(
                        title = document.getString("title") ?: "",
                        shortDescription = document.getString("shortDescription") ?: "",
                        fromMapAddress = document.getString("fromMapAddress") ?: "",
                        toMapAddress = document.getString("toMapAddress") ?: "",
                        fromPoint = Point(
                            fromGeoPoint?.latitude ?: 0.0,
                            fromGeoPoint?.longitude ?: 0.0
                        ),
                        toPoint = Point(
                            toGeoPoint?.latitude ?: 0.0,
                            toGeoPoint?.longitude ?: 0.0
                        ),
                        date = document.getString("date") ?: "",
                        money = (document.get("money") as? Number)?.toInt() ?: 0,
                        distance = (document.get("distance") as? Number)?.toFloat() ?: 0f,
                        uid = (document.get("uid") as? Number)?.toInt(),
                        phoneNumber = document.getString("phoneNumber")
                    )
                } catch (_: Exception) {
                    null
                }
            }
        } catch (_: Exception) {
            emptyList()
        }
    }


    /**
     * Получает завершенные заказы из локальной БД.
     * @return Flow со списком завершенных заказов
     */
    override fun fetchFinishOrders(): Flow<List<FinishOrder>> {
        return finishOrderDao.fetchOrders().map {
            it.map { item ->
                FinishOrder(
                    title = item.title,
                    money = item.money,
                    orderAddress = item.fromMapAddress,
                    isSuccessful = true,
                )
            }
        }
    }

    /**
     * Получает текущие заказы из локальной БД.
     * @return Flow со списком текущих заказов
     */
    override fun fetchCurrentOrders(): Flow<List<Order>> {
        return currentOrderDao.fetchOrders().map { it ->
            it.map { item ->
                Order(
                    title = item.title,
                    shortDescription = item.shortDescription,
                    fromMapAddress = item.fromMapAddress,
                    toMapAddress = item.toMapAddress,
                    fromPoint = Point(item.fromPointLat, item.fromPointLong),
                    toPoint = Point(item.toPointLat, item.toPointLong),
                    distance = calculateDistance(
                        Point(item.fromPointLat, item.fromPointLong),
                        Point(item.toPointLat, item.toPointLong)
                    ),
                    date = item.date,
                    money = item.money,
                    phoneNumber = item.phoneNumber,
                    uid = item.uid
                )
            }
        }
    }

    /**
     * Добавляет заказ в список текущих.
     * @param order Заказ для добавления
     */
    override suspend fun insertCurrentOrder(order: Order) {
        val currentOrderDB = CurrentOrderDB(
            title = order.title,
            shortDescription = order.shortDescription,
            fromMapAddress = order.fromMapAddress,
            toMapAddress = order.toMapAddress,
            fromPointLat = order.fromPoint.latitude,
            fromPointLong = order.fromPoint.longitude,
            toPointLat = order.toPoint.latitude,
            toPointLong = order.toPoint.longitude,
            date = order.date,
            money = order.money,
            phoneNumber = order.phoneNumber,
        )
        currentOrderDao.insert(currentOrderDB)
    }

    /**
     * Добавляет заказ в список завершенных.
     * @param order Заказ для добавления
     * @param isSuccessful Флаг успешного завершения
     */
    override suspend fun insertFinishOrder(order: Order, isSuccessful: Boolean) {
        val finishOrderDB = FinishOrderDB(
            title = order.title,
            money = order.money,
            orderAddress = order.fromMapAddress,
            isSuccessful = isSuccessful
        )
        finishOrderDao.insert(finishOrderDB)
    }


    /**
     * Удаляет текущий заказ по ID.
     * @param uid ID заказа для удаления
     */
    override suspend fun deleteCurrentOrder(uid: Int) {
        currentOrderDao.deleteOrder(uid)
    }

    /**
     * Сортирует список заказов.
     * @param orders Список для сортировки
     * @return Отсортированный список (по названию, дате и расстоянию)
     */
    fun sortOrders(orders: List<Order>): List<Order> {
        return orders.sortedWith(
            compareBy<Order> { it.title }
                .thenBy { it.date }
                .thenBy { it.distance }
        )
    }

    /**
     * Генерирует случайный тестовый заказ.
     * @return Сгенерированный заказ
     */
    private fun getOrder(): Order {
        val from = (0 until listPointRandom.size).random()
        var random = from
        while (random == from) random = (0 until listPointRandom.size).random()
        val to = random
        return Order(
            title = titles.random(),
            shortDescription = shortDescription.random(),
            fromMapAddress = listPointRandom[from].second,
            toMapAddress = listPointRandom[to].second,
            fromPoint = listPointRandom[from].first,
            toPoint = listPointRandom[to].first,

            date = "18-01-2024, 12:00",
            3000,
            distance = calculateDistance(
                listPointRandom[from].first,
                listPointRandom[to].first
            ),
            null,
            "+7-999-111-3333"
        )
    }

    companion object {

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

        val listPointRandom = listOf(
            Pair(Point(47.22663, 38.89492), "24-й пер., 26А, Таганрог, Ростовская обл."),
            Pair(Point(47.227, 38.9), "19-й пер., 8, Таганрог, Ростовская обл."),
            Pair(
                Point(47.218141482568534, 38.900777457819785),
                "11-й пер., 38, Таганрог, Ростовская обл."
            ),
            Pair(
                Point(47.21759604627763, 38.92000393179145),
                "Гоголевский пер., Таганрог, Ростовская обл."
            ),
            Pair(
                Point(47.22606532282803, 38.92929017948028),
                "Чеботарская ул., 31, Таганрог, Ростовская обл."
            ),
            Pair(
                Point(47.21764527984687, 38.909682670319086),
                "ул. Кузнечная, 50, Таганрог, Ростовская обл."
            ),
            Pair(
                Point(47.203507161076665, 38.907367617756194),
                "Смирновский пер., Таганрог, Ростовская обл."
            ),


            )

        /**
         * Вычисляет расстояние между двумя точками.
         * @param point1 Первая точка
         * @param point2 Вторая точка
         * @return Расстояние в километрах с точностью до 3 знаков
         */
        fun calculateDistance(point1: Point, point2: Point): Float {
            val location1 = Location("location1")
            location1.latitude = point1.latitude
            location1.longitude = point1.longitude
            val location2 = Location("location1")
            location2.latitude = point2.latitude
            location2.longitude = point2.longitude

            return ("%.3f".format(location1.distanceTo(location2) / 1000)).toFloat()
        }

    }
}
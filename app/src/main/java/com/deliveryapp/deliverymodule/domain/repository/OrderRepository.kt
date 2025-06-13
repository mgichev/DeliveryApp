// Автор: Гичев М. А., КТбо4-8
// Тема: ВКР. Разработка мобильного приложения для работы курьера
// Описание: Интерфейс репозитория заказов для поддержания принципов чистой архитектуры

package com.deliveryapp.deliverymodule.domain.repository

import com.deliveryapp.deliverymodule.domain.model.FinishOrder
import com.deliveryapp.deliverymodule.domain.model.Order
import kotlinx.coroutines.flow.Flow

/**
 * Репозиторий для работы с заказами.
 * Обеспечивает доступ к данным о новых, текущих и завершенных заказах.
 */
interface OrderRepository {

    /**
     * Получает список новых заказов.
     * @return Список объектов Order
     */
    suspend fun fetchNewOrders() : List<Order>

    /**
     * Получает поток завершенных заказов.
     * @return Flow с изменяемым списком FinishOrder
     */
    fun fetchFinishOrders() : Flow<List<FinishOrder>>

    /**
     * Получает поток текущих заказов.
     * @return Flow с изменяемым списком Order
     */
    fun fetchCurrentOrders() : Flow<List<Order>>

    /**
     * Добавляет заказ в список текущих.
     * @param order Объект Order для добавления
     */
    suspend fun insertCurrentOrder(order: Order)

    /**
     * Добавляет заказ в список завершенных.
     * @param order Объект Order для добавления
     * @param isSuccessful Флаг успешного завершения заказа
     */
    suspend fun insertFinishOrder(order: Order, isSuccessful: Boolean)

    /**
     * Удаляет текущий заказ по идентификатору.
     * @param uid Идентификатор заказа для удаления
     */
    suspend fun deleteCurrentOrder(uid: Int)
}
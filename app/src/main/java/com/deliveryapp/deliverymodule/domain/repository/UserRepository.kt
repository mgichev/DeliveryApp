// Автор: Гичев М. А., КТбо4-8
// Тема: ВКР. Разработка мобильного приложения для работы курьера
// Описание: Интерфейс репозитория данных пользователя для поддержания принципов чистой архитектуры

package com.deliveryapp.deliverymodule.domain.repository

import com.deliveryapp.deliverymodule.domain.model.Card
import com.deliveryapp.deliverymodule.domain.model.Category
import com.deliveryapp.deliverymodule.domain.model.PersonalInfo
import com.deliveryapp.deliverymodule.domain.model.Statistic
import com.deliveryapp.deliverymodule.domain.model.User

/**
 * Репозиторий для работы с данными пользователя.
 * Предоставляет методы для доступа и управления персональными данными,
 * статистикой, платежными картами и выбранными категориями.
 */
interface UserRepository {

    /**
     * Получает персональную информацию пользователя.
     * @param userId Уникальный идентификатор пользователя
     * @return Объект PersonalInfo или null, если данные не найдены
     */
    suspend fun getPersonalInfo(userId: String): PersonalInfo?

    /**
     * Получает статистику работы пользователя.
     * @param userId Уникальный идентификатор пользователя
     * @return Объект Statistic или null, если данные не найдены
     */
    suspend fun getStatistic(userId: String): Statistic?

    /**
     * Получает список платежных карт пользователя.
     * @param userId Уникальный идентификатор пользователя
     * @return Список объектов Card (может быть пустым)
     */
    suspend fun getCards(userId: String): List<Card>

    /**
     * Добавляет или обновляет данные пользователя.
     * @param user Объект User с данными для сохранения
     */
    suspend fun addUser(user: User)

    /**
     * Получает выбранные пользователем категории доставки.
     * @param userId Уникальный идентификатор пользователя
     * @return Множество объектов Category (может быть пустым)
     */
    suspend fun getSelectedCategories(userId: String): Set<Category>

    /**
     * Сохраняет выбранные пользователем категории доставки.
     * @param userId Уникальный идентификатор пользователя
     * @param categories Множество объектов Category для сохранения
     */
    suspend fun saveSelectedCategories(userId: String, categories: Set<Category>)
}
// Автор: Гичев М. А., КТбо4-8
// Тема: ВКР. Разработка мобильного приложения для работы курьера
// Описание: Репозиторий для работы с данными пользователями

package com.deliveryapp.deliverymodule.data

import com.deliveryapp.deliverymodule.domain.model.User
import com.deliveryapp.deliverymodule.domain.model.Card
import com.deliveryapp.deliverymodule.domain.model.Category
import com.deliveryapp.deliverymodule.domain.model.PersonalInfo
import com.deliveryapp.deliverymodule.domain.model.Statistic
import com.deliveryapp.deliverymodule.domain.repository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * Реализация репозитория для работы с данными пользователя.
 * Обеспечивает взаимодействие с Firestore для хранения и получения:
 * - Персональной информации
 * - Статистики
 * - Банковских карт
 * - Выбранных категорий
 */
class UserRepositoryImpl(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : UserRepository {

    /**
     * Получает персональную информацию пользователя.
     * @param userId Уникальный идентификатор пользователя
     * @return Объект PersonalInfo или null если данные не найдены
     */
    override suspend fun getPersonalInfo(userId: String): PersonalInfo? {
        val userDoc = firestore.collection("users").document(userId).get().await()
        if (!userDoc.exists()) return null

        val infoMap = userDoc.get("personalInfo") as? Map<String, String> ?: return null

        return PersonalInfo(
            fio = infoMap["fio"] ?: "",
            phoneNumber = infoMap["phoneNumber"] ?: "",
            address = infoMap["address"] ?: "",
            passport = infoMap["passport"] ?: "",
            inn = infoMap["inn"] ?: ""
        )
    }

    /**
     * Получает статистику пользователя.
     * @param userId Уникальный идентификатор пользователя
     * @return Объект Statistic или null если данные не найдены
     */
    override suspend fun getStatistic(userId: String): Statistic? {
        val userDoc = firestore.collection("users").document(userId).get().await()
        if (!userDoc.exists()) return null

        val statMap = userDoc.get("statistic") as? Map<String, String> ?: return null

        return Statistic(
            salaryMonth = statMap["salaryMonth"] ?: "",
            salaryYear = statMap["salaryYear"] ?: "",
            salaryTotal = statMap["salaryTotal"] ?: "",
            workMonth = statMap["workMonth"] ?: "",
            workYear = statMap["workYear"] ?: "",
            workTotal = statMap["workTotal"] ?: ""
        )
    }

    /**
     * Получает список банковских карт пользователя.
     * @param userId Уникальный идентификатор пользователя
     * @return Список карт (может быть пустым)
     */
    override suspend fun getCards(userId: String): List<Card> {
        val cardsSnapshot = firestore.collection("users")
            .document(userId)
            .collection("cards")
            .get()
            .await()

        return cardsSnapshot.documents.map { doc ->
            Card(
                number = doc.getString("number") ?: "",
                date = doc.getString("date") ?: ""
            )
        }
    }

    /**
     * Получает выбранные категории пользователя.
     * @param userId Уникальный идентификатор пользователя
     * @return Множество категорий (может быть пустым)
     */
    override suspend fun getSelectedCategories(userId: String): Set<Category> {
        val doc = firestore.collection("users").document(userId).get().await()
        if (!doc.exists()) return emptySet()

        val categoriesList = doc.get("selectedCategories") as? List<String> ?: return emptySet()

        return categoriesList.mapNotNull { name ->
            try {
                Category.valueOf(name)
            } catch (e: IllegalArgumentException) {
                null
            }
        }.toSet()
    }

    /**
     * Сохраняет выбранные категории пользователя.
     * @param userId Уникальный идентификатор пользователя
     * @param categories Множество категорий для сохранения
     */
    override suspend fun saveSelectedCategories(userId: String, categories: Set<Category>) {
        val categoriesList = categories.map { it.name }

        firestore.collection("users").document(userId)
            .update("selectedCategories", categoriesList)
            .await()
    }

    /**
     * Добавляет нового пользователя в БД пользователей на сервере
     * Сохраняет:
     * - Основную информацию (email)
     * - Персональные данные
     * - Статистику
     * - Выбранные категории
     * @param user Объект пользователя для сохранения
     */
    override suspend fun addUser(user: User) {
        firestore.collection("users")
            .document(user.uid)
            .set(
                mapOf(
                    "email" to user.email,
                    "personalInfo" to mapOf(
                        "fio" to user.personalInfo?.fio,
                        "phoneNumber" to user.personalInfo?.phoneNumber,
                        "address" to user.personalInfo?.address,
                        "passport" to user.personalInfo?.passport,
                        "inn" to user.personalInfo?.inn
                    ),
                    "statistic" to mapOf(
                        "salaryMonth" to user.statistic?.salaryMonth,
                        "salaryYear" to user.statistic?.salaryYear,
                        "salaryTotal" to user.statistic?.salaryTotal,
                        "workMonth" to user.statistic?.workMonth,
                        "workYear" to user.statistic?.workYear,
                        "workTotal" to user.statistic?.workTotal
                    ),
                    "selectedCategories" to user.selectedCategories.map { it.name }
                )
            ).await()
    }
}
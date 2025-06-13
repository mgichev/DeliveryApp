package com.deliveryapp.deliverymodule.data

import com.deliveryapp.deliverymodule.domain.User
import com.deliveryapp.deliverymodule.domain.model.Card
import com.deliveryapp.deliverymodule.domain.model.Category
import com.deliveryapp.deliverymodule.domain.model.PersonalInfo
import com.deliveryapp.deliverymodule.domain.model.Statistic
import com.deliveryapp.deliverymodule.domain.repository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : UserRepository {
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

    override suspend fun saveSelectedCategories(userId: String, categories: Set<Category>) {
        val categoriesList = categories.map { it.name }

        firestore.collection("users").document(userId)
            .update("selectedCategories", categoriesList)
            .await()
    }

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
package com.deliveryapp.deliverymodule.domain.repository

import com.deliveryapp.deliverymodule.domain.User
import com.deliveryapp.deliverymodule.domain.model.Card
import com.deliveryapp.deliverymodule.domain.model.Category
import com.deliveryapp.deliverymodule.domain.model.PersonalInfo
import com.deliveryapp.deliverymodule.domain.model.Statistic

interface UserRepository {
    suspend fun getPersonalInfo(userId: String): PersonalInfo?
    suspend fun getStatistic(userId: String): Statistic?
    suspend fun getCards(userId: String): List<Card>
    suspend fun addUser(user: User)
    suspend fun getSelectedCategories(userId: String): Set<Category>
    suspend fun saveSelectedCategories(userId: String, categories: Set<Category>)
}
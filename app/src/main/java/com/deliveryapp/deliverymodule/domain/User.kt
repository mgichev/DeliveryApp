package com.deliveryapp.deliverymodule.domain

import com.deliveryapp.deliverymodule.domain.model.Card
import com.deliveryapp.deliverymodule.domain.model.Category
import com.deliveryapp.deliverymodule.domain.model.PersonalInfo
import com.deliveryapp.deliverymodule.domain.model.Statistic

data class User(
    val uid: String,
    val email: String,
    val personalInfo: PersonalInfo? = null,
    val cards: List<Card> = emptyList(),
    val statistic: Statistic? = null,
    val selectedCategories: Set<Category> = emptySet()
)
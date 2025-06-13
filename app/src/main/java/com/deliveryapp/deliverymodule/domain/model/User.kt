package com.deliveryapp.deliverymodule.domain.model

data class User(
    val uid: String,
    val email: String,
    val personalInfo: PersonalInfo? = null,
    val cards: List<Card> = emptyList(),
    val statistic: Statistic? = null,
    val selectedCategories: Set<Category> = emptySet()
)
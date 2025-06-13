// Автор: Гичев М. А., КТбо4-8
// Тема: ВКР. Разработка мобильного приложения для работы курьера
// Описание: Модель данных завершенного заказа

package com.deliveryapp.deliverymodule.domain.model

data class FinishOrder(
    val title: String,
    val money: Int,
    val orderAddress: String,
    val isSuccessful: Boolean,
)
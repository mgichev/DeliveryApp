// Автор: Гичев М. А., КТбо4-8
// Тема: ВКР. Разработка мобильного приложения для работы курьера
// Описание: Модель данных, которая содержит персональные данные пользователя

package com.deliveryapp.deliverymodule.domain.model

/**
 * Модель персональных данных пользователя.
 * Содержит идентификационную и контактную информацию о пользователе.
 *
 * @property fio ФИО
 * @property phoneNumber Номер телефона в формате
 * @property address Полный почтовый адрес
 * @property passport Серия и номер паспорта в формате
 * @property inn Идентификационный номер налогоплательщика
 */
data class PersonalInfo(
    val fio: String,
    val phoneNumber: String,
    val address: String,
    val passport: String,
    val inn: String,
)
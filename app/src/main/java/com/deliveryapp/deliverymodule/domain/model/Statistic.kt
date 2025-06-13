// Автор: Гичев М. А., КТбо4-8
// Тема: ВКР. Разработка мобильного приложения для работы курьера
// Описание: Модель данных, которая содержит статистику пользователя

package com.deliveryapp.deliverymodule.domain.model

/**
 * Модель статистических данных о работе и заработке.
 * Содержит информацию о заработной плате и отработанном времени за различные периоды.
 *
 * @property salaryMonth Зарплата за текущий месяц в формате "12 345 ₽"
 * @property salaryYear Зарплата за текущий год в формате "123 456 ₽"
 * @property salaryTotal Общий заработок за все время в формате "1 234 567 ₽"
 * @property workMonth Отработанное время за месяц в формате "45 ч 30 мин"
 * @property workYear Отработанное время за год в формате "540 ч 15 мин"
 * @property workTotal Общее отработанное время в формате "2 340 ч 45 мин"
 */
data class Statistic(
    val salaryMonth: String,
    val salaryYear: String,
    val salaryTotal: String,
    val workMonth: String,
    val workYear: String,
    val workTotal: String
)
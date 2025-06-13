// Автор: Гичев М. А., КТбо4-8
// Тема: ВКР. Разработка мобильного приложения для работы курьера
// Описание: Классы для работы с сервером логгирования
package com.deliveryapp.deliverymodule.data.retrofit

/**
 * Класс для запроса данных с локального сервера логгирования.
 * Используется для отправки данных пользователя на сервер.
 *
 * @property name Имя пользователя
 * @property date Дата и время с устройства пользователя
 * @property device Информация об устройстве
 */
data class LogRequest(val name: String, val date: String, val device: String)

/**
 * Класс ответа сервера
 *
 * @property response Ответ сервера
 */
data class LogResponse(val response: String)
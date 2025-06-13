// Автор: Гичев М. А., КТбо4-8
// Тема: ВКР. Разработка мобильного приложения для работы курьера
// Описание: Интерфейс для работы по локальной сети по механизму REST

package com.deliveryapp.deliverymodule.data.retrofit

import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Интерфейс сервиса для отправки логов на сервер через Retrofit.
 * Определяет конечные точки API для логирования событий.
 */
interface LogService {
    @POST("/log")
    suspend fun log(@Body request: LogRequest): LogResponse
}
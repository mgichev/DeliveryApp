// Автор: Гичев М. А., КТбо4-8
// Тема: ВКР. Разработка мобильного приложения для работы курьера
// Описание: Интерфейс репозитория логгирования для поддержания принципов чистой архитектуры

package com.deliveryapp.deliverymodule.domain.repository

import com.deliveryapp.deliverymodule.data.retrofit.LogResponse

/**
 * Репозиторий для отправки логов на сервер.
 * Предоставляет метод для асинхронной отправки лог-записей.
 */
interface LogRepository {

    /**
     * Отправляет лог-запись на сервер.
     * @return Ответ сервера в виде объекта LogResponse
     */
    suspend fun log() : LogResponse
}
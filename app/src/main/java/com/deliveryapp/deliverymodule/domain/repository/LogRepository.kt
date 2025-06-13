package com.deliveryapp.deliverymodule.domain.repository

import com.deliveryapp.deliverymodule.data.retrofit.LogResponse

interface LogRepository {
    suspend fun log() : LogResponse
}
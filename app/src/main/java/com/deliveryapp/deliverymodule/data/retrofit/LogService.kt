package com.deliveryapp.deliverymodule.data.retrofit

import retrofit2.http.Body
import retrofit2.http.POST

interface LogService {
    @POST("/log")
    suspend fun log(@Body request: LogRequest): LogResponse
}
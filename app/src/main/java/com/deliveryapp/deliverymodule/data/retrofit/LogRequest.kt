package com.deliveryapp.deliverymodule.data.retrofit

data class LogRequest(val name: String, val date: String, val device: String)

data class LogResponse(val response: String)
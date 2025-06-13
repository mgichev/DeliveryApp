package com.deliveryapp.deliverymodule.data

import com.deliveryapp.deliverymodule.data.retrofit.LogRequest
import com.deliveryapp.deliverymodule.data.retrofit.LogResponse
import com.deliveryapp.deliverymodule.data.retrofit.LogService
import com.deliveryapp.deliverymodule.domain.repository.LogRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

class LogRepositoryImpl : LogRepository {

    override suspend fun log(): LogResponse {

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.150:5000")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()

        val service = retrofit.create(LogService::class.java)

        val androidVersion = getDeviceName()
        val date = getDate()
        return service.log(LogRequest("Name", date, androidVersion))
    }

    private fun getDeviceName(): String {
        val androidName = android.os.Build.VERSION.RELEASE ?: "null"
        val model =
            "${android.os.Build.MANUFACTURER} " + " ${android.os.Build.MODEL} Android: " + (android.os.Build.VERSION.RELEASE
                ?: "")
        return model
    }

    private fun getDate(): String {
        val timeZone = TimeZone.getDefault()
        val format = SimpleDateFormat("HH-mm-ss, yyyy-MM-dd, 'GMT-Z'", Locale.getDefault())
        format.timeZone = timeZone
        val time = format.format(Date())
        return time
    }
}
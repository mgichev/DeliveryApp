package com.deliveryapp

import android.app.Application
import com.deliveryapp.di.dataModule
import com.deliveryapp.di.viewModel
import com.example.deliveryapp.BuildConfig
import com.yandex.mapkit.MapKitFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {


    override fun onCreate() {

        val api = BuildConfig.YANDEX_MAP_SDK_API
        MapKitFactory.setApiKey(api)

        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(viewModel, dataModule)
        }

    }

    companion object {
        const val STATIC_MAP_API = BuildConfig.API_STATIC_MAP
    }
}
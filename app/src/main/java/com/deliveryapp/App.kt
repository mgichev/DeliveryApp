package com.deliveryapp

import android.app.Application
import com.deliveryapp.deliverymodule.di.dbModule
import com.deliveryapp.deliverymodule.di.viewModel
import com.example.deliveryapp.BuildConfig
import com.yandex.mapkit.MapKitFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {




    override fun onCreate() {

        val api = BuildConfig.YANDEX_MAP_SDK_API
        MapKitFactory.setApiKey(api)
        _application = this

        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(viewModel, dbModule)
        }

    }

    companion object {
        const val STATIC_MAP_API = BuildConfig.API_STATIC_MAP

        private lateinit var _application: Application
        val application: Application
            get() = _application

    }

}
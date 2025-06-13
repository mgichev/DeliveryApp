package com.deliveryapp.deliverymodule.di

import android.content.Context
import androidx.room.Room
import com.deliveryapp.deliverymodule.data.LogRepositoryImpl
import com.deliveryapp.deliverymodule.data.OrderRepositoryImpl
import com.deliveryapp.deliverymodule.data.UserRepositoryImpl
import com.deliveryapp.deliverymodule.data.db.OrdersDB
import com.deliveryapp.deliverymodule.domain.repository.LogRepository
import com.deliveryapp.deliverymodule.domain.repository.OrderRepository
import com.deliveryapp.deliverymodule.domain.repository.UserRepository
import org.koin.dsl.module

val dbModule = module {
    single<OrderRepository> {
        OrderRepositoryImpl(currentOrderDao = get(), finishOrderDao = get())
    }

    single<UserRepository> {
        UserRepositoryImpl()
    }

    single {
        provideDB(context = get())
    }

    single {
        provideCurrentDao(db = get())
    }

    single {
        provideFinishDao(db = get())
    }

    single {
        provideNewDao(db = get())
    }

    single<LogRepository> {
        LogRepositoryImpl()
    }
}

fun provideDB(context: Context) = Room.databaseBuilder(
    context, OrdersDB::class.java, "currentOrdersDB"
).build()

fun provideCurrentDao(db: OrdersDB) = db.currentOrderDao()
fun provideFinishDao(db: OrdersDB) = db.finishOrderDao()
fun provideNewDao(db: OrdersDB) = db.currentOrderDao()
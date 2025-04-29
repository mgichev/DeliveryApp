package com.deliveryapp.deliverymodule.di

import android.content.Context
import androidx.room.Room
import com.deliveryapp.deliverymodule.data.OrderRepositoryImpl
import com.deliveryapp.deliverymodule.data.db.OrdersDB
import com.deliveryapp.deliverymodule.domain.OrderRepository
import org.koin.dsl.module

val dbModule = module {
    single<OrderRepository> {
        OrderRepositoryImpl(currentOrderDao = get(), finishOrderDao = get())
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
}

fun provideDB(context: Context) = Room.databaseBuilder(
    context, OrdersDB::class.java, "currentOrdersDB"
).build()

fun provideCurrentDao(db: OrdersDB) = db.currentOrderDao()
fun provideFinishDao(db: OrdersDB) = db.finishOrderDao()
fun provideNewDao(db: OrdersDB) = db.currentOrderDao()
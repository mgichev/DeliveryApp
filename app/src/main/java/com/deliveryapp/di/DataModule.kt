package com.deliveryapp.di

import android.content.Context
import androidx.room.Room
import com.deliveryapp.data.CurrentOrdersDB
import com.deliveryapp.data.OrderRepositoryImpl
import com.deliveryapp.domain.OrderRepository
import org.koin.dsl.module

val dataModule = module {
    single<OrderRepository> {
        OrderRepositoryImpl(currentOrderDao = get())
    }

    single {
        provideDB(context = get())
    }

    single {
        provideDAO(db = get())
    }
}

fun provideDB(context: Context) = Room.databaseBuilder(
    context,
    CurrentOrdersDB::class.java,
    "currentOrdersDB"
).build()

fun provideDAO(db: CurrentOrdersDB) = db.dao()
package com.deliveryapp.di

import com.deliveryapp.ui.orders.OrdersViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModel = module {
    viewModel {
        OrdersViewModel(orderRepository = get())
    }
}
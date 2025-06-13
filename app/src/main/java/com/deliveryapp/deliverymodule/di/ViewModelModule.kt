package com.deliveryapp.deliverymodule.di

import com.deliveryapp.authmodule.ui.AuthViewModel
import com.deliveryapp.deliverymodule.ui.account.AccountViewModel
import com.deliveryapp.deliverymodule.ui.orders.OrdersViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModel = module {
    viewModel {
        OrdersViewModel(orderRepository = get())
    }

    viewModel {
        AuthViewModel(userRepository = get())
    }

    viewModel {
        AccountViewModel(logRepository = get(), userRepository = get())
    }
}
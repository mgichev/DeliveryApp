// Автор: Гичев М. А., КТбо4-8
// Тема: ВКР. Разработка мобильного приложения для работы курьера
// Описание: Файл необходим для объявления компонентов, которые используются для внедрения зависимостей
// Внедрение зависимостей для view-model классов

package com.deliveryapp.deliverymodule.di

import com.deliveryapp.authmodule.ui.viewmodel.AuthViewModel
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
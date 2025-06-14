// Автор: Гичев М. А., КТбо4-8
// Тема: ВКР. Разработка мобильного приложения для работы курьера
// Описание: основное окно для навигации и фрагментов экрана модуля работы курьеров

package com.deliveryapp.deliverymodule.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.deliveryapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_nav)
        bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val isNoBottomNav = when (destination.id) {
                R.id.infoFragment -> true
                else -> false
            }
            bottomNav.visibility = if(isNoBottomNav) View.GONE else View.VISIBLE
        }
    }
}
package com.deliveryapp.auth_module

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.example.deliveryapp.R

class AuthActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
//
//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.nav_auth_host) as NavHostFragment
//        navController = navHostFragment.navController
    }
}
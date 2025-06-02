package com.deliveryapp.authmodule.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.deliveryapp.deliverymodule.ui.MainActivity
import com.example.deliveryapp.R
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_auth_host) as NavHostFragment
        navController = navHostFragment.navController

        val toolbar = findViewById<Toolbar>(R.id.toolbarId)
        toolbar.setNavigationOnClickListener {
            navController.popBackStack()
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.AuthFragment -> toolbar.visibility = View.GONE
                else -> toolbar.visibility = View.VISIBLE
            }
        }

    }
}
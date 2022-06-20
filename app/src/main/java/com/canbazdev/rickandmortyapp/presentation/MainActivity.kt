package com.canbazdev.rickandmortyapp.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.canbazdev.rickandmortyapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        installSplashScreen()
        var bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavbar)
        val navigationHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navigationHost.findNavController()
        bottomNavigationView.inflateMenu(R.menu.bottom)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.onBoardingFragment -> bottomNavigationView.visibility = View.GONE
                R.id.splashFragment -> bottomNavigationView.visibility = View.GONE
                else -> bottomNavigationView.visibility = View.VISIBLE
            }
        }

        bottomNavigationView.setupWithNavController(navController)
    }

    private fun setUpBottomNavigationView() {

    }
}
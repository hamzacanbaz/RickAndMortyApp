package com.canbazdev.rickandmortyapp.presentation

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.canbazdev.rickandmortyapp.R
import com.canbazdev.rickandmortyapp.R.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        val bottomNavigationView: BottomNavigationView = findViewById(id.bottomNavbar)
        val navigationHost =
            supportFragmentManager.findFragmentById(id.nav_host_fragment) as NavHostFragment
        val navController = navigationHost.findNavController()
        val toolbar = findViewById<Toolbar>(id.toolbar)
        bottomNavigationView.inflateMenu(menu.bottom)
        setUpBottomNavigationView(navController, bottomNavigationView, toolbar)
        setUpToolbar(navController, toolbar)

    }


    private fun setUpBottomNavigationView(
        navController: NavController,
        bottomNavigationView: BottomNavigationView,
        toolbar: Toolbar
    ) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                id.onBoardingFragment -> bottomNavigationView.visibility = View.GONE
                id.splashFragment -> {
                    toolbar.visibility = View.GONE
                    bottomNavigationView.visibility = View.GONE
                }
                id.charactersFragment -> {
                    toolbar.visibility = View.VISIBLE
                    bottomNavigationView.visibility = View.VISIBLE
                }
                else -> bottomNavigationView.visibility = View.VISIBLE

            }
        }
        bottomNavigationView.setupWithNavController(navController)
    }

    private fun setUpToolbar(navController: NavController, toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                id.charactersFragment,
                id.locationsFragment,
                id.episodesFragment
            ),
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }



}
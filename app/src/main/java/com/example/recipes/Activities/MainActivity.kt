package com.example.recipes.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.recipes.R
import com.example.recipes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfigurations: AppBarConfiguration
    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        //setting up the bottom navigation
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHost
        navController = navHost.navController

        binding.bottomNav.setupWithNavController(navController)
        appBarConfigurations = AppBarConfiguration(setOf(R.id.fragment_home, R.id.fragment_likes))
        //setting up actionBar support
     //   setupActionBarWithNavController(navController,appBarConfigurations)




    }
}
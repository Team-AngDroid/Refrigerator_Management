package com.angdroid.refrigerator_manament.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.ActivityHomeBinding
import com.angdroid.refrigerator_manament.presentation.camera.CameraActivity
import com.angdroid.refrigerator_manament.presentation.util.BaseActivity


class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setNavigation()
        setOnClickListener()
    }

    private fun setNavigation() {
        val host = supportFragmentManager.findFragmentById(R.id.nav_container) as NavHostFragment?
            ?: return
        navController = host.navController
        binding.bottomNavHome.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.appbarHome.appbar.title = when (destination.id) {
                R.id.fragment_recipe -> destination.label
                R.id.fragment_refrigerator -> destination.label
                else -> throw IllegalAccessException("Error.NavController")
            }
        }
    }

    private fun setOnClickListener(){
        binding.fabCamera.setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))
        }
    }


}
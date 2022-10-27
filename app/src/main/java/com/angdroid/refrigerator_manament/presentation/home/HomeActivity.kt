package com.angdroid.refrigerator_manament.presentation.home

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.ActivityHomeBinding
import com.angdroid.refrigerator_manament.presentation.camera.CameraActivity
import com.angdroid.refrigerator_manament.presentation.util.BaseActivity
import com.angdroid.refrigerator_manament.presentation.util.makeSnackbar
import com.angdroid.refrigerator_manament.presentation.util.setOnSingleClickListener
import com.angdroid.refrigerator_manament.presentation.util.types.Food
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    private val recipeViewModel : RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this

        setRecipe()
        setNavigation()
        setOnClickListener()
    }

    private fun setRecipe(){
        val recipeSet = mutableSetOf<String>()
        val foodSet = mutableSetOf<String>()
        while (recipeSet.size < 2){
            recipeSet.add(Food.FOOD[(0..Food.FOOD.size).random()])
            foodSet.add(Food.FOOD[(0..Food.FOOD.size).random()])
        }
        recipeViewModel.getIngredientRecipe(recipeSet.toList())
    }

    private fun setNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_container) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.bottom_nav_graph)
        navController.graph = navGraph

        binding.bottomNavHome.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.appbarHome.title = when (destination.id) {
                R.id.fragment_recipe -> destination.label
                R.id.fragment_refrigerator -> destination.label
                R.id.fragment_search -> destination.label
                else -> throw IllegalAccessException("Error.NavController")
            }
        }
    }

    private fun setOnClickListener() {

        binding.fabCamera.setOnSingleClickListener {
            if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
                showPermissionContextPopup()
            } else {
                requestPermissions(
                    arrayOf(android.Manifest.permission.CAMERA),
                    1000
                )
            }
        }
    }

    private fun showPermissionContextPopup() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.permission_agree))
            .setMessage(R.string.permission_reason)
            .setPositiveButton(R.string.permission_agree) { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.CAMERA), 1000)
            }
            .setNegativeButton(R.string.permission_cancel) { _, _ -> }
            .create()
            .show()
    }

    // 권한 요청 승인 이후 실행되는 함수
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1000 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(
                        Intent(this, CameraActivity::class.java)
                            .setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    )
                } else
                    binding.root.makeSnackbar("권한 요청이 거절되었습니다.")
            }
            else -> {
                throw IllegalAccessException("Error.Permission")
            }
        }
    }

}



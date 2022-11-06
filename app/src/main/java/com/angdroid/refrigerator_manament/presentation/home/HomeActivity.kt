package com.angdroid.refrigerator_manament.presentation.home

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.ActivityHomeBinding
import com.angdroid.refrigerator_manament.presentation.camera.CameraActivity
import com.angdroid.refrigerator_manament.presentation.home.viewmodel.RecipeViewModel
import com.angdroid.refrigerator_manament.presentation.util.BaseActivity
import com.angdroid.refrigerator_manament.presentation.util.makeSnackbar
import com.angdroid.refrigerator_manament.presentation.util.setOnSingleClickListener
import com.angdroid.refrigerator_manament.presentation.util.types.FoodIdType
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    private val recipeViewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this

        setRecipe()
        setNavigation()
        setOnClickListener()
    }

    private fun setRecipe() {
        // 해당 작업을 액티비티에서 해준 이유
        // fragment에서 해당 로직을 해준다면, 프래그먼트를 전환할때 마다 레시피가 랜덤하게 호출되고
        // 그렇게 되면 서버호출이 과도하게 많아진다는 느낌이 들어서 액티비티에서만 random Recipe를 호출해주고 
        // 프래그먼트 전환간 서버호출이 발생하지 않도록 조절
        val recipeSet = mutableSetOf<String>()
        val foodSet = mutableSetOf<String>()
        while (recipeSet.size < 2) {
            recipeSet.add(FoodIdType.values()[(0 until FoodIdType.values().size).random(Random(System.nanoTime()))].name)
            foodSet.add(FoodIdType.values()[(0 until FoodIdType.values().size).random(Random(System.nanoTime()))].name)
        }
        recipeViewModel.getRandomRecipe(recipeSet.toList()) // 랜덤 레시피 두개 받아오는 작업
        recipeViewModel.getIngredientRecipe(foodSet.toList()) // 랜덤 재료 레시피를 받아오는 작업
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



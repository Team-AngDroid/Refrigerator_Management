package com.angdroid.refrigerator_manament.presentation.home

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.ActivityHomeBinding
import com.angdroid.refrigerator_manament.presentation.camera.CameraXSourceDemoActivity
import com.angdroid.refrigerator_manament.presentation.home.fragment.RefrigeratorFragment
import com.angdroid.refrigerator_manament.presentation.home.viewmodel.RecipeViewModel
import com.angdroid.refrigerator_manament.presentation.util.BaseActivity
import com.angdroid.refrigerator_manament.presentation.util.makeSnackbar
import com.angdroid.refrigerator_manament.presentation.util.setOnSingleClickListener
import com.angdroid.refrigerator_manament.presentation.util.types.FoodIdType
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    private val recipeViewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        intent?.let {
            binding.ivBitmap.setImageBitmap(it.getByteArrayExtra("img")
                ?.let { it1 -> byteArrayToBitmap(it1) })
        }
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = token.toString()
            Log.e("TAG", msg)
        })
        setRecipe()
        setNavigation()
        setOnClickListener()
    }

    private fun setRecipe() {
        // ?????? ????????? ?????????????????? ?????? ??????
        // fragment?????? ?????? ????????? ????????????, ?????????????????? ???????????? ?????? ???????????? ???????????? ????????????
        // ????????? ?????? ??????????????? ???????????? ??????????????? ????????? ????????? ????????????????????? random Recipe??? ???????????????
        // ??????????????? ????????? ??????????????? ???????????? ????????? ??????
        val recipeSet = mutableSetOf<String>()
        val foodSet = mutableSetOf<String>()
        while (recipeSet.size < 2) {
            recipeSet.add(
                FoodIdType.values()[listOf(0, 1, 4, 5, 6, 13).random(
                    Random(
                        System.nanoTime()
                    )
                )].name
            )
            foodSet.add(FoodIdType.values()[(0 until FoodIdType.values().size).random(Random(System.nanoTime()))].name)
        }
        recipeViewModel.getRandomRecipe(recipeSet.toList()) // ?????? ????????? ?????? ???????????? ??????
        recipeViewModel.getIngredientRecipe(foodSet.toList()) // ?????? ?????? ???????????? ???????????? ??????
    }

    private fun byteArrayToBitmap(byteArray:ByteArray): Bitmap? {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
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

    override fun onRestart() {
        super.onRestart()
        when (binding.bottomNavHome.selectedItemId) {
            R.id.fragment_refrigerator -> {
                supportFragmentManager.findFragmentById(R.id.nav_container)?.childFragmentManager?.fragments?.let { (it[0] as RefrigeratorFragment).reFreshState() }
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

    // ?????? ?????? ?????? ?????? ???????????? ??????
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
                        Intent(this, CameraXSourceDemoActivity::class.java)
                            .setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    )
                } else
                    binding.root.makeSnackbar("?????? ????????? ?????????????????????.")
            }
            else -> {
                throw IllegalAccessException("Error.Permission")
            }
        }
    }

}



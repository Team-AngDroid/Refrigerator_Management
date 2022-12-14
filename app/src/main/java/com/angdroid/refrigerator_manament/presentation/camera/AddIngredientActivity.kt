package com.angdroid.refrigerator_manament.presentation.camera

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.angdroid.refrigerator_manament.BuildConfig
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.data.datasource.user.UserInfoDataSource
import com.angdroid.refrigerator_manament.data.datasource.user.UserInfoDataSourceImpl
import com.angdroid.refrigerator_manament.databinding.ActivityAddIngredientBinding
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import com.angdroid.refrigerator_manament.presentation.camera.adapter.AddIngredientAdapter
import com.angdroid.refrigerator_manament.presentation.camera.viewmodel.AddIngredientViewModel
import com.angdroid.refrigerator_manament.presentation.custom.CustomDialog
import com.angdroid.refrigerator_manament.presentation.util.BaseActivity
import com.angdroid.refrigerator_manament.presentation.util.safeLet
import com.angdroid.refrigerator_manament.presentation.util.safeValueOf
import com.angdroid.refrigerator_manament.presentation.util.types.DefaultExpirationDate
import com.angdroid.refrigerator_manament.presentation.util.types.FoodIdType
import com.angdroid.refrigerator_manament.util.collectFlowWhenStarted
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.time.LocalDate
import java.util.*

@AndroidEntryPoint
class AddIngredientActivity :
    BaseActivity<ActivityAddIngredientBinding>(R.layout.activity_add_ingredient) {

    private lateinit var adapter: AddIngredientAdapter
    private val addIngredientViewModel: AddIngredientViewModel by viewModels()
    private lateinit var foodInfoDataSource: UserInfoDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        foodInfoDataSource = UserInfoDataSourceImpl(
            Firebase.firestore.collection("User").document(BuildConfig.USER_ID)
        )
        intent?.let {
            addIngredientViewModel.setIntentFoodList(getIngredients())
        }
        setAppbar()
        initAdapter()
        binding.btnIngredientsAdd.setOnClickListener {
            addIngredientViewModel.setFoodList(cacheDir) { success ->
                if (success) finish()
            }
        }
    }

    private fun initBinding() {
        binding.lifecycleOwner = this
        binding.addIngredientViewModel = addIngredientViewModel
    }

    private fun setAppbar() {
        binding.appbarIngredient.topAppbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.top_back -> {
                    try {
                        cacheDir.listFiles()?.map { file -> file.delete() }
                    } catch (_: Exception) {
                    }
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    private fun initAdapter() {
        adapter = AddIngredientAdapter(
            this,
            itemDeleteListener = {
                addIngredientViewModel.removeItem(it)
            },
            itemMinusListener = {
                addIngredientViewModel.minusItemCount(it)
            },
            itemPlusListener = {
                addIngredientViewModel.plusItemCount(it)
            },
            itemDialogListener = {
                CustomDialog(this, itemAddListener = {
                    addIngredientViewModel.addDialogFood(it)
                }).showDialog()
            }
        )
        binding.rcvIngredients.adapter = adapter
        collectFoodList()
    }

    private fun collectFoodList() {
        collectFlowWhenStarted(addIngredientViewModel.foodList) {
            adapter.submitList(it.toList())
        }
    }

    private fun getIngredients(): List<IngredientType.Food> {
        safeLet(
            intent.getStringExtra("img"),
            intent.getStringExtra("label")
        ) { file, nameList ->
            val ingredients = mutableListOf<IngredientType.Food>()
            safeValueOf<FoodIdType>(nameList)?.let { food ->
                IngredientType.Food(
                    fid = UUID.randomUUID().toString(), //fid??? ?????? ?????? ??????
                    foodId = food.foodId,
                    expirationDate = LocalDate.now()
                        .plusDays(DefaultExpirationDate.valueOf(food.name).expiration.toLong()), //?????? Food??? Default ???????????? ????????????
                    name = food.name,
                    image = file,
                    categoryId = food.returnCategoryType(),
                    foodCount = 1
                )
            }?.let {
                ingredients.add(
                    it
                )
            }

            ingredients.add(
                0, IngredientType.Food("0", 0, LocalDate.now(), "", "", 0, 0)
                // ????????? [?????? ??????]???????????? ???????????????
            )
            return ingredients.toList()
        }
        return listOf(
            IngredientType.Food(
                "0",
                0,
                LocalDate.now(),
                "",
                "",
                0,
                0
            )
        ) //???????????? ????????? ?????? ????????? add ??????
    }

    /**
     * List<FilePath: String> ????????? List<Bitmap>?????? ??????????????? Converter
     */
    private fun fileToBitmapConverter(fileList: List<String>): List<Bitmap> {
        return try {
            fileList.map { imagePath ->
                BitmapFactory.decodeFile("$cacheDir/$imagePath")
            }
        } catch (_: java.lang.Exception) {
            listOf()
        }
    }

    /**
     * List<ByteArray: Image> ????????? List<Bitmap>?????? ??????????????? Converter
     */
    private fun byteArrayToBitmap(byteArrays: List<ByteArray>): List<Bitmap?> {
        return byteArrays.map { BitmapFactory.decodeByteArray(it, 0, it.size) }
    }
}
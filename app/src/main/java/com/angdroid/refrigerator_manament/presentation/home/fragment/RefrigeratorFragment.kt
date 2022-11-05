package com.angdroid.refrigerator_manament.presentation.home.fragment

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import coil.load
import com.angdroid.refrigerator_manament.BuildConfig
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.FragmentRefrigeratorBinding
import com.angdroid.refrigerator_manament.presentation.custom.DynamicGridLayoutManager
import com.angdroid.refrigerator_manament.presentation.detail.DetailActivity
import com.angdroid.refrigerator_manament.presentation.home.adapter.CategoryListAdapter
import com.angdroid.refrigerator_manament.presentation.home.viewmodel.IngredientViewModel
import com.angdroid.refrigerator_manament.presentation.util.BaseFragment
import com.angdroid.refrigerator_manament.presentation.util.getSpanSizeLookUp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.util.UUID


@AndroidEntryPoint
class RefrigeratorFragment :
    BaseFragment<FragmentRefrigeratorBinding>(R.layout.fragment_refrigerator) {
    private lateinit var adapter: CategoryListAdapter
    private val ingredientViewModel: IngredientViewModel by viewModels()
    private val storageInstance: FirebaseStorage by lazy { Firebase.storage }

    private val currentUserRef = storageInstance.reference
    private var path: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.ingredientViewModel = ingredientViewModel
        setAdapter()
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.kiwi)
        val byteArray = bitmapToByteArray(bitmap)
        val returnBitmap = byteArrayToBitmap(byteArray)


        val outputStream = ByteArrayOutputStream()
        returnBitmap?.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
        val selectedImageBytes = outputStream.toByteArray()
        //upLoadImage(selectedImageBytes)

        binding.imageView.setOnClickListener {
            pathToReference("/x2pt8UANvml9SldmWItQ/2c5529bb-b61c-3116-8f01-056eeb6f03d1").downloadUrl.addOnCompleteListener {
                binding.imageView.load(it.result)
            }
        }
    }

    private fun upLoadImage(byteArray: ByteArray) {
        uploadMessageImage(byteArray) { imagePath ->
            path = imagePath
            Firebase.firestore.collection("Food").document("foodbase").set(mapOf("id" to imagePath))
        }
    }

    fun pathToReference(path: String) = storageInstance.getReference(path)

    private fun uploadMessageImage(
        imageBytes: ByteArray,
        onSuccess: (imagePath: String) -> Unit
    ) {
        val ref =
            currentUserRef.child("${BuildConfig.USER_ID}/${UUID.nameUUIDFromBytes(imageBytes)}") //store image files in the firestorage
        ref.putBytes(imageBytes).addOnSuccessListener {

            onSuccess(ref.path)
        }
    }

    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        return stream.toByteArray()
    }

    // Byte를 Bitmap으로 변환
    private fun byteArrayToBitmap(byteArray: ByteArray): Bitmap? {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    private fun setAdapter() {
        adapter = CategoryListAdapter { item ->
            activity?.startActivity(
                Intent(
                    requireContext(),
                    DetailActivity::class.java
                ).apply {
                    this.putExtra("foodName", item)
                })
        }
        binding.rvList.layoutManager =
            DynamicGridLayoutManager(requireContext(), adapter.getSpanSizeLookUp())
        binding.rvList.adapter = adapter
    }
}
package com.angdroid.refrigerator_manament.presentation.custom

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.ArrayAdapter
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.DialogAddIngredientsBinding
import com.angdroid.refrigerator_manament.presentation.util.dpToPx

class CustomDialog(val context: Context) {
    val binding: DialogAddIngredientsBinding = DialogAddIngredientsBinding.inflate(
        LayoutInflater.from(context)
    )

    fun showDialog() {


        val dialog = Dialog(context)

        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(binding.root)
            val params: WindowManager.LayoutParams = dialog.window!!.attributes
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // 배경색 투명

            params.width = WindowManager.LayoutParams.MATCH_PARENT
            params.height = WindowManager.LayoutParams.WRAP_CONTENT
            (binding.root.layoutParams as ViewGroup.MarginLayoutParams).apply {
                marginEnd = 16.dpToPx(binding.root.context)
                marginStart = 16.dpToPx(binding.root.context)
            } // 레이아웃 속성 재 적용

            dialog.window!!.attributes = params
        }
        setSpinner()
        setListener(dialog)

        dialog.show()
    }


    private fun setSpinner() {
        ArrayAdapter.createFromResource(context, R.array.spinner_ingredients, R.layout.spinner_list)
            .also { adapter ->
                adapter.setDropDownViewResource(R.layout.spinner_list)
                binding.spinnerCategory.adapter = adapter
            }


    }

    private fun setListener(dialog: Dialog){
        binding.ivDelete.setOnClickListener {
            dialog.dismiss()
        }
        binding.btnIngredientsAdd.setOnClickListener {
            //TODO
        }
    }

}
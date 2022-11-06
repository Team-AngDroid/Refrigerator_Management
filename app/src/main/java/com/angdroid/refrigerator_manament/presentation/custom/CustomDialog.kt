package com.angdroid.refrigerator_manament.presentation.custom

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.DialogAddIngredientsBinding
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import com.angdroid.refrigerator_manament.presentation.util.dpToPx
import com.angdroid.refrigerator_manament.presentation.util.makeToast
import com.angdroid.refrigerator_manament.presentation.util.types.FoodTypeFeatures
import com.angdroid.refrigerator_manament.presentation.util.types.FoodIdType
import java.time.LocalDate


class CustomDialog(
    val context: Context,
    private val itemAddListener: (IngredientType.Food) -> Unit
) {


    private val activationList = mutableListOf<Boolean>(false, false)

    // 재료명 입력 여부 / 카테고리 선택 여부
    private var ingredientCount = 1 // 초기 카운트값
    private val inflater by lazy { LayoutInflater.from(context) }
    private lateinit var dialog: Dialog


    val binding: DialogAddIngredientsBinding = DialogAddIngredientsBinding.inflate(inflater)

    fun showDialog() {
        binding.enabled = false
        binding.count = ingredientCount

        dialog = Dialog(context)
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
        setListener(itemAddListener)
        dialog.show()
    }


    private fun setSpinner() {
        val items = context.resources.getStringArray(R.array.spinner_ingredients)
        val spinnerAdapter = object : ArrayAdapter<String>(context, R.layout.spinner_list) {

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent)
                if (position == count) {
                    //마지막 포지션의 textView 를 힌트 용으로 사용
                    (v.findViewById<View>(R.id.tv_spinner_item) as TextView).text = ""
                    //아이템의 마지막 값을 불러와 hint로 추가
                    (v.findViewById<View>(R.id.tv_spinner_item) as TextView).hint = getItem(count)
                }
                return v
            }

            override fun getCount(): Int {
                //마지막 아이템은 힌트용으로만 사용하기 때문에 getCount에 1을 빼줌
                return super.getCount() - 1
            }
        }
        spinnerAdapter.addAll(items.toMutableList())
        spinnerAdapter.add(context.getString(R.string.category_choice))
        binding.spinnerCategory.apply {
            adapter = spinnerAdapter
            setSelection(spinnerAdapter.count)
            onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View,
                        position: Int,
                        id: Long
                    ) {

                        //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작
                        when (position) { // 포지션 함수
                            0, 1, 2, 3, 4 -> {
                                activationList[1] = true
                                checkEnabled()
                            }
                            else -> {}
                        }
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {}
                }
        }
    }

    private fun setListener(itemAddListener: (IngredientType.Food) -> Unit) {

        binding.etIngredient.addTextChangedListener {
            activationList[0] = !it!!.isEmpty()
            checkEnabled()
        }

        binding.ivPlus.setOnClickListener {
            ingredientCount += 1
            binding.count = ingredientCount
        }
        binding.ivMinus.setOnClickListener {
            if (ingredientCount != 1) {
                ingredientCount -= 1
                binding.count = ingredientCount
            }
        }

        binding.btnIngredientsAdd.setOnClickListener {
            if (activationList.all { it } && checkFoodName(binding.etIngredient.text.toString())) {
                itemAddListener(
                    IngredientType.Food(
                        "123",
                        findFoodId(binding.etIngredient.text.toString()),
                        LocalDate.now(),
                        binding.etIngredient.text.toString(),
                        "",
                        binding.spinnerCategory.selectedItemPosition + 1,
                        ingredientCount
                    )
                )
                dialog.cancel()

            } else {
                binding.etIngredient.makeToast("재료 입력을 정확히 해주세요!")
            }
        }
    }

    private fun checkEnabled() {
        binding.enabled = activationList.all { it }
    }


    private fun checkFoodName(name: String): Boolean {
        FoodTypeFeatures.values().map { it.name }.forEach {
            if (name == it)
                return true
        }
        return false
    }

    private fun findFoodId(name: String): Int {
        return FoodIdType.valueOf(name).foodId
    }
}
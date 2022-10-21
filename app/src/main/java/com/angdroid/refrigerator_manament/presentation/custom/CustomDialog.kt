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
import com.angdroid.refrigerator_manament.presentation.camera.AddIngredientActivity
import com.angdroid.refrigerator_manament.presentation.util.dpToPx
import com.angdroid.refrigerator_manament.presentation.util.makeToast
import timber.log.Timber
import java.time.LocalDate


class CustomDialog(val context: Context) {

    private lateinit var ingredient: IngredientType.Food
    private val activationList = mutableListOf<Boolean>(false, false, false)

    // 재료명 입력 여부 / 카테고리 선택 여부 / count 0 이 아닌 경우
    private val inflater by lazy { LayoutInflater.from(context) }
    private var category: Int = 0
    private lateinit var dialog : Dialog


    val binding: DialogAddIngredientsBinding = DialogAddIngredientsBinding.inflate(inflater)

    fun showDialog() {
        binding.count = 0.toString()

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
        setListener()
        dialog.show()
    }


    private fun setSpinner() {
        val items = context.resources.getStringArray(R.array.spinner_ingredients)
        val spinnerAapter = object : ArrayAdapter<String>(context, R.layout.spinner_list) {

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
        spinnerAapter.addAll(items.toMutableList())
        spinnerAapter.add(context.getString(R.string.category_choice))
        binding.spinnerCategory.apply {
            adapter = spinnerAapter
            setSelection(spinnerAapter.count)
            onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View,
                        position: Int,
                        id: Long
                    ) {
                        activationList[1] = true
                        //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작
                        when (position) { // 카테고리 선택
                            0 -> {
                                category = 1
                            }
                            1 -> {
                                category = 2
                            }
                            2 -> {
                                category = 3
                            }
                            3 -> {
                                category = 4
                            }
                            4 -> {
                                category = 5
                            }
                            else -> {}
                        }
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {}
                }
        }
    }

    private fun setListener() {

        binding.etIngredient.addTextChangedListener {
            activationList[0] = !it!!.isEmpty()
            Timber.e("hi")
        }
        binding.ivPlus.setOnClickListener {
            binding.count = (binding.count!!.toInt() + 1).toString()
            activationList[2] = true
        }
        binding.ivMinus.setOnClickListener {
            if (binding.count!!.toInt() != 0) {
                binding.count = (binding.count!!.toInt() - 1).toString()
                activationList[2] = true
            } else
                activationList[2] = false
        }
        binding.btnIngredientsAdd.setOnClickListener {
            if (activationList.all { it } && checkFoodname(binding.etIngredient.text.toString())) {
                AddIngredientActivity.Companion.setIngredient(
                    IngredientType.Food(
                        "123",
                        findFoodId(binding.etIngredient.text.toString()),
                        LocalDate.now(),
                        binding.etIngredient.text.toString(),
                        "",
                        category,
                        binding.count!!.toInt()
                    )
                )
                dialog.cancel()
            } else {
                binding.root.makeToast("재료 입력을 다 해주세요!")
            }
        }
    }

    private fun checkFoodname(name: String): Boolean {
        when (name) {
            "당근" -> return true
            "오이" -> return true
            "무우" -> return true
            "무" -> return true
            "사과" -> return true
            "배" -> return true
            "귤" -> return true
            "계란" -> return true
            "생닭" -> return true
            "우유" -> return true
            "치즈" -> return true
            "새우" -> return true
            "오징어" -> return true
            "고등어" -> return true
        }
        return false
    }

    private fun findFoodId(name: String): Int {
        when (name) {
            "당근" -> return 101
            "오이" -> return 102
            "무우" -> return 103
            "무" -> return 103
            "사과" -> return 104
            "배" -> return 105
            "귤" -> return 106
            "계란" -> return 107
            "생닭" -> return 108
            "우유" -> return 109
            "치즈" -> return 110
            "새우" -> return 111
            "오징어" -> return 112
            "고등어" -> return 113
        }
        return 0
    }
}
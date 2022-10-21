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


class CustomDialog(val context: Context) {

    private lateinit var ingredient: IngredientType.Food
    private val activationList = mutableListOf<Boolean>(false, false, false)
    // 재료명 입력 여부 / 카테고리 선택 여부 / count 0 이 아닌 경우

    private val inflater by lazy { LayoutInflater.from(context) }
    val binding: DialogAddIngredientsBinding = DialogAddIngredientsBinding.inflate(inflater)

    fun showDialog() {
        binding.count = 0.toString()
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
                            0 -> {}
                            1 -> {}
                            2 -> {}
                            3 -> {}
                            4 -> {}
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
    }
}
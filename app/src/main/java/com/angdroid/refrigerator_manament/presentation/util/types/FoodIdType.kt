package com.angdroid.refrigerator_manament.presentation.util.types

enum class FoodIdType(val foodId: Int) {
    당근(101), 오이(102), 무우(103),
    사과(104), 배(105), 귤(106), 바나나(107),
    오렌지(108), 포도(109), 망고(110), 키위(111),
    계란(112), 생닭(113), 우유(114),
    치즈(115), 새우(116), 오징어(117), 고등어(118);

    fun returnCategoryType() = when (foodId) {
        in 101..103 -> 1
        in 104..111 -> 2
        in 112..113 -> 3
        in 114..115 -> 4
        in 116..118 -> 5
        else -> 0
    }
    fun isFinalConsonant() = when(name){
        "당근", "계란", "생닭" -> true
        else -> false
    }
}
package com.angdroid.refrigerator_manament.domain.usecase

interface GetRecipeDetail { //레시피 하나의 상세내용을 가져오는 함수
    operator fun invoke():List<String>
}
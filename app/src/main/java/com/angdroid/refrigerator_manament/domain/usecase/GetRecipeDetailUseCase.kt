package com.angdroid.refrigerator_manament.domain.usecase

interface GetRecipeDetailUseCase { //매개변수로 넣은 레시피 하나의 상세내용을 가져오는 함수
    operator fun invoke():List<String>
//TODO    operator fun invoke(recipe:Recipe):List<String>
}
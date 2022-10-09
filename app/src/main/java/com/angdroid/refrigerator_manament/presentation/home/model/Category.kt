package com.angdroid.refrigerator_manament.presentation.home.model

data class Category(val categoryName:String, val categoryCount:Int, val categoryId:Int):BaseType{
    override fun getType():Int = categoryId
    override fun getCount(): Int = categoryCount
}

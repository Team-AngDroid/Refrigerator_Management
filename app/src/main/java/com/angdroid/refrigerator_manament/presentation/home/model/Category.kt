package com.angdroid.refrigerator_manament.presentation.home.model

data class Category(val categoryName:String, val categoryCount:Int, private val categoryId:Int):BaseType{
    override fun getType():Int = categoryId
    override fun getCount(): Int = categoryCount
    override fun getCategoryId(): Int = categoryId
}

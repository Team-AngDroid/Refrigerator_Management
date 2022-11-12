package com.angdroid.refrigerator_manament.application

import android.app.Application
import com.angdroid.refrigerator_manament.BuildConfig
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    companion object {
        lateinit var userIngredientInfo: List<String> // Caching 느낌으로 남겨두자,, 얘만은 어떻게 하기가 애매함, BindingAdapter에도 붙어 있어서,,
        lateinit var userIngredient: List<IngredientType.Food> // Caching 느낌으로 남겨두자,, 얘만은 어떻게 하기가 애매함, BindingAdapter에도 붙어 있어서,,
        private lateinit var instance: App
        fun getUserIngredientInfoInitialized(): Boolean = ::userIngredientInfo.isInitialized
        fun setUserInfo(ingredients:List<IngredientType.Food>){
            userIngredient = ingredients
            userIngredientInfo = ingredients.map { it.name }
        }
    }
}
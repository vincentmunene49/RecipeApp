package com.example.recipes.ViewModel

import androidx.lifecycle.LiveData
import com.example.recipes.RoomDb.Recipe
import com.example.recipes.RoomDb.RecipeDao
import com.example.recipes.retrofit.RetrofitApi

class RetRepository(val retrofitApi: RetrofitApi) {
    fun getMeals(key:String,id:String,value: String,type:String,random:Boolean) = retrofitApi.getMeals(key, id, value, type, random)
}

class RecipeDatabaseRepository(val db_dao:RecipeDao){
    val readRecipe:LiveData<List<Recipe>> = db_dao.readRecipe()

    suspend fun insertRecipe(recipe: Recipe) = db_dao.insertRecipe(recipe)
}
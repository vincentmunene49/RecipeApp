package com.example.recipes.ViewModel

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipes.RoomDb.Recipe
import com.example.recipes.RoomDb.RecipeDao
import com.example.recipes.retrofit.RetrofitApi

class RetRepository(val retrofitApi: RetrofitApi) {
    fun getMeals(key:String,id:String,value: String,type:String,random:Boolean) = retrofitApi.getMeals(key, id, value, type, random)
}

class RecipeDatabaseRepository(val db_dao:RecipeDao){
    val readRecipe:LiveData<List<Recipe>> = db_dao.readRecipe()

    suspend fun insertRecipe(recipe: Recipe) = db_dao.insertRecipe(recipe)

    suspend fun deleteRecipe(recipe: Recipe) = db_dao.deleteRecipe(recipe)
    suspend fun deleteAllReipes() = db_dao.deleteAllRecipes()
    fun rowExixts(url:String):LiveData<Int> = db_dao.rowExists(url)

}
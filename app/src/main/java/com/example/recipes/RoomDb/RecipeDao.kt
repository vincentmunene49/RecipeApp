package com.example.recipes.RoomDb

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecipeDao {
    //insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe:Recipe)
    //Delete
    @Delete
    suspend fun deleteRecipe(recipe: Recipe)
    //DELETE ALL
    @Query("DELETE FROM recipe_table")
    suspend fun deleteAllRecipes()
    //select
    @Query("SELECT * FROM recipe_table")
    fun readRecipe():LiveData<List<Recipe>>
    //select boolean value
    @Query("SELECT isChecked FROM recipe_table WHERE url = :url")
    fun select_is_checked(url:String):Cursor



}
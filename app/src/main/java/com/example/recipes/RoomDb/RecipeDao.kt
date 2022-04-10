package com.example.recipes.RoomDb

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
    @Query("SELECT * FROM recipe_table ORDER BY id ASC")
    fun readRecipe():LiveData<List<Recipe>>



}
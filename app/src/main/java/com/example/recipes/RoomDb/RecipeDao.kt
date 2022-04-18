package com.example.recipes.RoomDb

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface RecipeDao {
    //insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe)

    //Delete
    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    //DELETE ALL
    @Query("DELETE FROM recipe_table")
    suspend fun deleteAllRecipes()

    //select
    @Query("SELECT * FROM recipe_table")
    fun readRecipe(): LiveData<List<Recipe>>

    //select boolean value
    @Query("SELECT COUNT(url) FROM RECIPE_TABLE WHERE url = :url ")
    fun rowExists(url: String):LiveData<Int>


}
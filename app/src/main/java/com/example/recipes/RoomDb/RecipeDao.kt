package com.example.recipes.RoomDb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipeDao {
    //insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecipe(recipe:Recipe)
    //select
    @Query("SELECT * FROM recipe_table ORDER BY id ASC")
    fun readRecipe():LiveData<List<Recipe>>
}
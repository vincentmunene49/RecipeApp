package com.example.recipes.RoomDb

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipes.Pojo.Ingredient

@Entity(tableName = "recipe_table")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val lable: String,
    val image: Bitmap,
    val ingredients: List<Ingredient>
)
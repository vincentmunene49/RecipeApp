package com.example.recipes.RoomDb

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipes.Pojo.Ingredient
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "recipe_table")
data class Recipe(
    @PrimaryKey
    val url:String,
    val lable: String,
    val image: Bitmap,
    val ingredients: List<Ingredient>
):Parcelable
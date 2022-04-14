package com.example.recipes.Pojo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ingredient(
    var food: String,
    var foodCategory: String,
    var foodId: String,
    var image: String,
    var measure: String,
    var quantity: Double,
    var text: String,
    var weight: Double
): Parcelable
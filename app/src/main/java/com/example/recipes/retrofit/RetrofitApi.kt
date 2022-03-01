package com.example.recipes.retrofit

import com.example.recipes.Pojo.Meal
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {
    @GET("/api/recipes/v2")
 fun getMeals(
        @Query("app_key") key: String,
        @Query("app_id") id: String,
        @Query("q") value: String,
        @Query("type") type: String,
        @Query("random") random: Boolean
    ): Call<Meal>
}
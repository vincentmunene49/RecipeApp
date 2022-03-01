package com.example.recipes.retrofit

import com.example.recipes.util.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: RetrofitApi by lazy {
        Retrofit.Builder().
        baseUrl(BASE_URL).
        addConverterFactory(GsonConverterFactory.create()).
        build().
        create(RetrofitApi::class.java)
    }
}
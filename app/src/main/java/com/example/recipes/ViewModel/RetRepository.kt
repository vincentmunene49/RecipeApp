package com.example.recipes.ViewModel

import com.example.recipes.retrofit.RetrofitApi

class RetRepository(val retrofitApi: RetrofitApi) {
    fun getMeals(key:String,id:String,value: String,type:String,random:Boolean) = retrofitApi.getMeals(key, id, value, type, random)
}
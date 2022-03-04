package com.example.recipes.Pojo

sealed class ScreenStates<T> (val data:T? = null,val message:String? = null){
    class Success<T>(data:T): ScreenStates <T>(data)

    class Loading<T>(data: T?):ScreenStates<T>(data)

    class Error<T>(message:String, data: T?):ScreenStates<T>(data,message)
}
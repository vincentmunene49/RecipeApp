package com.example.recipes.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipes.Pojo.Hit
import com.example.recipes.Pojo.Meal
import com.example.recipes.Pojo.ScreenStates
import com.example.recipes.util.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecViewModel(
    private val repository: RetRepository
) : ViewModel() {


    private var _meals = MutableLiveData<ScreenStates <List<Hit>?>>()
     val meals: LiveData<ScreenStates<List<Hit>?>>
        get() = _meals

    init {

            getMeals()


    }

    fun getMeals() {
        val response = repository.getMeals(KEY, ID, VALUE, TYPE, RANDOM)
        _meals.postValue(ScreenStates.Loading(null))
        response.enqueue(object : Callback<Meal> {
            override fun onResponse(call: Call<Meal>, response: Response<Meal>) {
                if (response.isSuccessful) {
                    _meals.postValue(ScreenStates.Success(response.body()?.hits))

                }else{
                    _meals.postValue(ScreenStates.Error(response.code().toString(),null))
                }
            }

            override fun onFailure(call: Call<Meal>, t: Throwable) {
                _meals.postValue(ScreenStates.Error(t.message.toString(),null))
              //  Log.d("dennis", "onFailure: ${t.message}")
            }

        })


    }

    fun onRefresh(){
        getMeals()
    }


}


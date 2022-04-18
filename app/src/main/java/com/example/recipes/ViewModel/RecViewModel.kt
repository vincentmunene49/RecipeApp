package com.example.recipes.ViewModel

import android.app.Application
import android.database.Cursor
import androidx.lifecycle.*
import com.example.recipes.Pojo.Hit
import com.example.recipes.Pojo.Meal
import com.example.recipes.Pojo.ScreenStates
import com.example.recipes.RoomDb.Recipe
import com.example.recipes.RoomDb.Recipe_database
import com.example.recipes.util.ID
import com.example.recipes.util.KEY
import com.example.recipes.util.RANDOM
import com.example.recipes.util.TYPE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecViewModel(
    private val repository: RetRepository,
) : ViewModel() {


    private var _meals = MutableLiveData<ScreenStates<List<Hit>?>>()
    val meals: LiveData<ScreenStates<List<Hit>?>>
        get() = _meals
    var meal = "chicken"

    init {

        getMeals(meal)


    }

    fun getMeals(meal: String) {

        val response = repository.getMeals(KEY, ID, meal, TYPE, RANDOM)
        _meals.postValue(ScreenStates.Loading(null))
        response.enqueue(object : Callback<Meal> {
            override fun onResponse(call: Call<Meal>, response: Response<Meal>) {
                if (response.isSuccessful) {
                    _meals.postValue(ScreenStates.Success(response.body()?.hits))

                } else {
                    _meals.postValue(ScreenStates.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<Meal>, t: Throwable) {
                _meals.postValue(ScreenStates.Error(t.message.toString(), null))
                //  Log.d("dennis", "onFailure: ${t.message}")
            }

        })


    }

    fun onRefresh() {
        getMeals(meal)
    }


}

class dbRecViewModel(application: Application) : AndroidViewModel(application) {
    val readRecipe: LiveData<List<Recipe>>

    private val repository: RecipeDatabaseRepository

    //checker
    private var _checker = MutableLiveData<Boolean>()
    val checker: LiveData<Boolean>
        get() = _checker


    init {
        val recipe_dao = Recipe_database.getDatabase(application).RecipeDao()
        repository = RecipeDatabaseRepository(recipe_dao)
        readRecipe = repository.readRecipe


    }


    fun insertRecipe(recipe: Recipe) {
        viewModelScope.launch(Dispatchers.IO + NonCancellable) {
            repository.insertRecipe(recipe)
        }
    }

    fun deleteRecipe(recipe: Recipe) {

        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteRecipe(recipe)
        }
    }

    fun deleteALlRecipe() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllReipes()
        }
    }

    fun checkcer(): Boolean {

        _checker.value = true

        return _checker.value!!
    }
    fun rowExists(url:String):LiveData<Int> {
      return   repository.rowExixts(url)
    }



}



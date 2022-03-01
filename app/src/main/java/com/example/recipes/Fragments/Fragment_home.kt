package com.example.recipes.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipes.Adapters.RecyclerAdapter
import com.example.recipes.Interfaces.itemClickedListener
import com.example.recipes.Pojo.Meal
import com.example.recipes.databinding.FragmentHomeBinding
import com.example.recipes.retrofit.RetrofitInstance
import com.example.recipes.util.ID
import com.example.recipes.util.KEY
import com.example.recipes.util.TYPE
import com.example.recipes.util.VALUE
import okio.IOException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class fragment_home : Fragment(), itemClickedListener {
    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

//Running out of main Thread
        lifecycleScope.launchWhenCreated {
            binding.progressBar.isVisible = true
            val response = try {
                RetrofitInstance.api.getMeals(KEY, ID, VALUE, TYPE, true)
            }catch (e: IOException){
                Log.e("vincent", "IOException", )
                return@launchWhenCreated
            }catch (e: HttpException){
                Log.e("dennis", "HTTPException", )
                return@launchWhenCreated
            }
            //retrofit
            response.enqueue(object : Callback<Meal> {
                override fun onResponse(call: Call<Meal>, response: Response<Meal>) {
                    val responseMeal = response.body()!!
                    //    Log.d("vincent", "onResponse: ${response.body()!!.hits[0].recipe.image} ")
                    //setting up recyler view
                    var recycler_view = binding.recView
                    recycler_view.layoutManager = GridLayoutManager(context, 2)
                    val adapter = RecyclerAdapter(responseMeal.hits)
                    recycler_view.adapter = adapter
                }

                override fun onFailure(call: Call<Meal>, t: Throwable) {
                    Log.d("dennis", "onFailure: ${t.message}")
                }
            })
            binding.progressBar.isVisible = false

        }

     //   val response = RetrofitInstance.api.getMeals(KEY, ID, VALUE, TYPE, true)



        return binding.root


    }

    override fun onDestroyView() {

        super.onDestroyView()
        _binding = null
    }

    //set up click action
    override fun onItemClicked(position: Int) {
//        val itemClicked = temp_data_source.getData()[position]
//
//            val image = itemClicked.image
//            val recipe = itemClicked.recipe
//            val action = fragment_homeDirections.actionFragmentHomeToRecipeFragment(image, recipe)
//            findNavController().navigate(action)


    }

//    private fun setLikes(checked: Boolean) {
//        var checked = checked
//        if (checked) {
//            checked = false
//
//        } else {
//            var favIcon = R.id.favourite
//
//        }
//    }


}


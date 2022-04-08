package com.example.recipes.Fragments

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.bumptech.glide.Glide
import com.example.recipes.Adapters.RecyclerAdapterIngredients
import com.example.recipes.Pojo.ObjectHit
import com.example.recipes.ViewModel.dbRecViewModel
import com.example.recipes.databinding.RecipeFragmentBinding
import kotlinx.coroutines.launch


class recipe_fragment:Fragment() {
    private val args:recipe_fragmentArgs by navArgs()//for passing arguments safely
    var _binding:RecipeFragmentBinding?= null
    val binding get() = _binding!!

    //database viewModel
    private lateinit var _dViewModel: dbRecViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RecipeFragmentBinding.inflate(inflater,container,false)

        binding.apply {
            Glide.with(this@recipe_fragment).load(args.image).into(foodImage)
           // foodImage.setImageResource(args.image)
            //collapsingToolBar.title = args.recipe

        }
        val ingredients = args.ingredients
        val adapter = RecyclerAdapterIngredients(ingredients)
        val recyler_view = binding.recipeRecView
        recyler_view.layoutManager = GridLayoutManager(context, 2)
        recyler_view.adapter = adapter

        //database view model
        _dViewModel = ViewModelProvider(this).get(dbRecViewModel::class.java)


//adding liked functionality

        binding.liked.setOnClickListener{
           val recipe_poition:Int = args.position

            var viewClicked = ObjectHit.hit[recipe_poition]

            lifecycleScope.launch {
                val recipe = com.example.recipes.RoomDb.Recipe(
                    0,
                    viewClicked.recipe.label,
                    getBitmap(viewClicked.recipe.image),
                    viewClicked.recipe.ingredients
                )

                _dViewModel.insertRecipe(recipe)
            }

            Toast.makeText(context, "liked recipe at $recipe_poition", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
    //convert image to bitmap
    private suspend fun getBitmap(imageUrl: String): Bitmap {
        val loading = ImageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data(imageUrl)
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable

        return (result as BitmapDrawable).bitmap

    }




    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }


}
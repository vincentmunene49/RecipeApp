package com.example.recipes.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.recipes.databinding.RecipeFragmentBinding

class recipe_fragment:Fragment() {
    private val args:recipe_fragmentArgs by navArgs()//for passing arguments safely
    var _binding:RecipeFragmentBinding?= null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RecipeFragmentBinding.inflate(inflater,container,false)

        binding.apply {
            Glide.with(this@recipe_fragment).load(args.image).into(foodImage)
           // foodImage.setImageResource(args.image)
            recipeView.text = args.recipe
        }

        return binding.root
    }
}
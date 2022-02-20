package com.example.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
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
            foodImage.setImageResource(args.image)
            recipeView.text = args.recipe
        }

        return binding.root
    }
}
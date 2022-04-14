package com.example.recipes.Fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.recipes.Adapters.RecyclerAdapterIngredients
import com.example.recipes.R
import com.example.recipes.ViewModel.dbRecViewModel
import com.example.recipes.databinding.OfflineRecipeFragmentBinding

class Offline_Recipe_Fragment : Fragment() {
    private var _binding: OfflineRecipeFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var _dViewModel:dbRecViewModel
    private val args: Offline_Recipe_FragmentArgs by navArgs()//for passing arguments safely

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = OfflineRecipeFragmentBinding.inflate(inflater, container, false)
//set options menu
        setHasOptionsMenu(true)
//initialize viewModel
        _dViewModel = ViewModelProvider(this).get(dbRecViewModel::class.java)
        val ingredients = args.recipe.ingredients.toTypedArray()
        val image = args.recipe.image
        //val label = args.recipe.lable

        binding.apply {
            Glide.with(this@Offline_Recipe_Fragment).load(image).into(foodImage)
            // foodImage.setImageResource(args.image)
            //collapsingToolBar.title = args.recipe

        }
        val adapter = RecyclerAdapterIngredients(ingredients)
        val recyler_view = binding.recipeRecView
        recyler_view.layoutManager = GridLayoutManager(context, 2)
        recyler_view.adapter = adapter
            //toolbar
        val toolBar = binding.toolBar
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolBar)
        (activity as AppCompatActivity).supportActionBar?.title = "Ingredients"


        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_recipe,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.delete_recipe){
            deleteRecipe()
        }


        return super.onOptionsItemSelected(item)
    }

    private fun deleteRecipe() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_ ->
            _dViewModel.deleteRecipe(args.recipe)
            Toast.makeText(requireContext(), "successfully Deleted", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_offline_Recipe_Fragment_to_fragment_likes)
        }
        builder.setNegativeButton("No"){_,_ -> }
        builder.setTitle("Delete Recipe?")
        builder.setMessage("Are you sure you want to delete Recipe?")
        builder.create().show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}



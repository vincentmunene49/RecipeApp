package com.example.recipes.Fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipes.Adapters.RecylerAdapterLikedList
import com.example.recipes.Interfaces.viewClickedListener
import com.example.recipes.R
import com.example.recipes.ViewModel.dbRecViewModel
import com.example.recipes.databinding.FragmentLikesBinding

class fragment_likes : Fragment(),viewClickedListener {
    private var _binding: FragmentLikesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RecylerAdapterLikedList

    //database viewModel
    private lateinit var _dViewModel: dbRecViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLikesBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        _dViewModel = ViewModelProvider(this).get(dbRecViewModel::class.java)

        //setup adapter
        adapter = RecylerAdapterLikedList(this)
        val recView = binding.likedRecView
        recView.layoutManager = GridLayoutManager(context, 2);
        recView.adapter = adapter

        _dViewModel.readRecipe.observe(viewLifecycleOwner, {
            adapter.setData(it)
        })

//toolbar
        val toolBar = binding.toolbar
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolBar)
        (activity as AppCompatActivity).supportActionBar?.title = "Favourites"


        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewClicked(position: Int) {
        val recipe = adapter.getRecipe(position)
        val action = fragment_likesDirections.actionFragmentLikesToOfflineRecipeFragment(recipe)

        findNavController().navigate(action)
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
            _dViewModel.deleteALlRecipe()
            Toast.makeText(requireContext(), "successfully Deleted", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){_,_ -> }
        builder.setTitle("Delete ALL Recipes?")
        builder.setMessage("Are you sure you want to delete All Recipe?")
        builder.create().show()
    }




}
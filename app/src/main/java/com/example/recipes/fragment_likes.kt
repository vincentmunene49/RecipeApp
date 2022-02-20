package com.example.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipes.databinding.FragmentLikesBinding

class fragment_likes:Fragment(),itemClickedListener {
    private  var _binding:FragmentLikesBinding?=null
    private  val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLikesBinding.inflate(inflater,container,false)

        //set adapter and view
        val adapter = RecylerAdapterLikedList(temp_data_source.likedList,this)
        val recView = binding.likedRecView
        recView.layoutManager = GridLayoutManager(this.context,2)
        recView.adapter = adapter

        return binding.root
    }

    override fun onItemClicked(position: Int) {
        val itemClicked = temp_data_source.likedList[position]

        val image = itemClicked.image
        val recipe = itemClicked.recipe
        val action = fragment_likesDirections.actionFragmentLikesToRecipeFragment(image,recipe)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
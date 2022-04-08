package com.example.recipes.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipes.Adapters.RecylerAdapterLikedList
import com.example.recipes.Interfaces.itemClickedListener
import com.example.recipes.ViewModel.dbRecViewModel
import com.example.recipes.databinding.FragmentLikesBinding

class fragment_likes : Fragment(), itemClickedListener {
    private var _binding: FragmentLikesBinding? = null
    private val binding get() = _binding!!

    //database viewModel
    private lateinit var _dViewModel: dbRecViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLikesBinding.inflate(inflater, container, false)
        setHasOptionsMenu(false)

        _dViewModel = ViewModelProvider(this).get(dbRecViewModel::class.java)

        //setup adapter
        val adapter = RecylerAdapterLikedList(this)
        val recView = binding.likedRecView
        recView.layoutManager = GridLayoutManager(context, 2);
        recView.adapter = adapter

        _dViewModel.readRecipe.observe(viewLifecycleOwner ,{
            adapter.setData(it)
        })
        return binding.root
    }


//        val itemClicked = temp_data_source.likedList[position]
//
//        val image = itemClicked.image
//        val recipe = itemClicked.recipe
//        val action = fragment_likesDirections.actionFragmentLikesToRecipeFragment(image,recipe)
//        findNavController().navigate(action)


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewClicked(position: Int) {
        TODO("Not yet implemented")
    }


}
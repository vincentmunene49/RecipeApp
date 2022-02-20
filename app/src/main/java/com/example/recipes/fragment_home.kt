package com.example.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipes.databinding.FragmentHomeBinding

class fragment_home : Fragment(), itemClickedListener {
    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
//setting up recyler view
        var recycler_view = binding.recView
        recycler_view.layoutManager = GridLayoutManager(this.context, 2)
        val adapter = RecyclerAdapter(temp_data_source.getData(), this)
        recycler_view.adapter = adapter

        return binding.root


    }

    override fun onDestroyView() {

        super.onDestroyView()
        _binding = null
    }

    //set up click action
    override fun onItemClicked(position: Int) {
        val itemClicked = temp_data_source.getData()[position]

            val image = itemClicked.image
            val recipe = itemClicked.recipe
            val action = fragment_homeDirections.actionFragmentHomeToRecipeFragment(image, recipe)
            findNavController().navigate(action)



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


package com.example.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipes.databinding.FragmentHomeBinding

class fragment_home : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        var recycler_view = binding.recView
        recycler_view.layoutManager = GridLayoutManager(this.context, 2)
        val adapter = RecyclerAdapter(temp_data_source.getData())
        recycler_view.adapter = adapter

       return binding.root


    }

    override fun onDestroyView() {

        super.onDestroyView()
        _binding = null
    }

}

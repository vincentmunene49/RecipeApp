package com.example.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recipes.databinding.FragmentLikesBinding

class fragment_likes:Fragment() {
    private  var _binding:FragmentLikesBinding?=null
    private  val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLikesBinding.inflate(inflater,container,false)

        

        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
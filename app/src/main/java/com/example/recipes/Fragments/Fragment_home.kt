package com.example.recipes.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipes.Adapters.RecyclerAdapter
import com.example.recipes.Interfaces.itemClickedListener
import com.example.recipes.Pojo.Hit
import com.example.recipes.Pojo.ObjectHit
import com.example.recipes.Pojo.ScreenStates
import com.example.recipes.ViewModel.RecViewModel
import com.example.recipes.ViewModel.RecViewModelFactory
import com.example.recipes.ViewModel.RetRepository
import com.example.recipes.databinding.FragmentHomeBinding
import com.example.recipes.retrofit.RetrofitInstance
import com.google.android.material.snackbar.Snackbar

class fragment_home : Fragment(), itemClickedListener {
    private val _mViewModel: RecViewModel by lazy {
        ViewModelProvider(this,RecViewModelFactory(RetRepository(RetrofitInstance.api))).get(RecViewModel::class.java)
    }

    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        _mViewModel.meals.observe(viewLifecycleOwner, {
            processScreenState(it)
        })
        binding.swiperefresh.setOnRefreshListener {
            binding.progressBar.isVisible = false
            _mViewModel.onRefresh()
            binding.swiperefresh.isRefreshing = false
        }



        return binding.root


    }

    //set up screen states
    private fun processScreenState(state: ScreenStates<List<Hit>?>){
        when(state){
            is ScreenStates.Loading ->
                binding.progressBar.visibility = View.VISIBLE
            is ScreenStates.Success ->{
                binding.progressBar.visibility = View.GONE
                if(state.data != null){
                    ObjectHit.hit = state.data as ArrayList<Hit>
                    val adapter = RecyclerAdapter(state.data, this@fragment_home)
                    val recyler_view = binding.recView
                    recyler_view.layoutManager = GridLayoutManager(context, 2);
                    recyler_view.adapter = adapter
                }
            }
            is ScreenStates.Error ->{
                binding.progressBar.visibility = View.GONE
                val view = binding.progressBar.rootView
                Snackbar.make(view,state.message!!,Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {

        super.onDestroyView()
        _binding = null
    }

    //set up click action
    override fun onItemClicked(position: Int) {
        var viewClicked = ObjectHit.hit[position]
        val image = viewClicked.recipe.image
        val recipe = viewClicked.recipe.label
        val ingredients = viewClicked.recipe.ingredients.toTypedArray()



        val action = fragment_homeDirections.actionFragmentHomeToRecipeFragment(recipe, image,ingredients)

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


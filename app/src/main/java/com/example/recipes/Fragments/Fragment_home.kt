package com.example.recipes.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipes.Adapters.RecyclerAdapter
import com.example.recipes.Interfaces.viewClickedListener
import com.example.recipes.Pojo.Hit
import com.example.recipes.Pojo.ObjectHit
import com.example.recipes.Pojo.ScreenStates
import com.example.recipes.R
import com.example.recipes.ViewModel.RecViewModel
import com.example.recipes.ViewModel.RecViewModelFactory
import com.example.recipes.ViewModel.RetRepository
import com.example.recipes.databinding.FragmentHomeBinding
import com.example.recipes.retrofit.RetrofitInstance
import com.google.android.material.snackbar.Snackbar

class fragment_home : Fragment(), viewClickedListener {
    //retrofit view Model
    private val _mViewModel: RecViewModel by lazy {
        ViewModelProvider(this, RecViewModelFactory(RetRepository(RetrofitInstance.api))).get(
            RecViewModel::class.java
        )
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
    private fun processScreenState(state: ScreenStates<List<Hit>?>) {


        when (state) {
            is ScreenStates.Loading ->
                binding.progressBar.visibility = View.VISIBLE
            is ScreenStates.Success -> {
                binding.progressBar.visibility = View.GONE
                //remove error fragment
                val fragment = childFragmentManager.findFragmentById(R.id.no_internet_fragment)
                if (fragment != null) {
                    childFragmentManager.commit {
                        setReorderingAllowed(true)
                        remove(fragment)
                     //   detach(fragment)
                        //hide(fragment)


                    }
                }


                if (state.data != null) {
                    ObjectHit.hit = state.data as ArrayList<Hit>
                    val adapter = RecyclerAdapter(state.data, this@fragment_home)
                    val recyler_view = binding.recView
                    recyler_view.layoutManager = GridLayoutManager(context, 2);
                    recyler_view.adapter = adapter
                    //searching...naive implementation

                    binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return true
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            if (newText!!.isNotEmpty()) {
                                state.data.clear()
                                _mViewModel.getMeals(newText)
                                adapter.notifyDataSetChanged()


                            } else {
                                state.data.clear()
                                _mViewModel.getMeals("chicken")
                                adapter.notifyDataSetChanged()
                            }
                            return true
                        }

                    })
                }
            }
            is ScreenStates.Error -> {
                binding.progressBar.visibility = View.GONE


                //offline fragment manager
                val fragment = childFragmentManager.findFragmentById(R.id.no_internet_fragment)
                if (fragment != null) {
                    //do nothing
                } else {
                    childFragmentManager.commit {
                        setReorderingAllowed(true)

                        add<NoInternetConnection>(R.id.no_internet_fragment)
                    }
                }


//                val view = binding.progressBar.rootView
//                Snackbar.make(view, state.message!!, Snackbar.LENGTH_LONG).show()


            }
        }
    }

    override fun onDestroyView() {

        super.onDestroyView()
        _binding = null
    }

    //set up click action for view
    override fun onViewClicked(position: Int) {
        var viewClicked = ObjectHit.hit[position]
        val image = viewClicked.recipe.image
        //val recipe = viewClicked.recipe.label
        val ingredients = viewClicked.recipe.ingredients.toTypedArray()
        val source = viewClicked.recipe.url


        val action = fragment_homeDirections.actionFragmentHomeToRecipeFragment(
            image,
            ingredients,
            position,
            source
        )

        findNavController().navigate(action)


    }


}


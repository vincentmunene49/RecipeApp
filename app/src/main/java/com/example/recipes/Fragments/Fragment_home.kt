package com.example.recipes.Fragments

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.recipes.Adapters.RecyclerAdapter
import com.example.recipes.Interfaces.itemClickedListener
import com.example.recipes.Pojo.Hit
import com.example.recipes.Pojo.ObjectHit
import com.example.recipes.Pojo.ScreenStates
import com.example.recipes.ViewModel.RecViewModel
import com.example.recipes.ViewModel.RecViewModelFactory
import com.example.recipes.ViewModel.RetRepository
import com.example.recipes.ViewModel.dbRecViewModel
import com.example.recipes.databinding.FragmentHomeBinding
import com.example.recipes.retrofit.RetrofitInstance
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class fragment_home : Fragment(), itemClickedListener {
    //retrofit view Model
    private val _mViewModel: RecViewModel by lazy {
        ViewModelProvider(this, RecViewModelFactory(RetRepository(RetrofitInstance.api))).get(
            RecViewModel::class.java
        )
    }

    //database viewModel
    private lateinit var _dViewModel: dbRecViewModel

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
//database view model
        _dViewModel = ViewModelProvider(this).get(dbRecViewModel::class.java)

        return binding.root


    }

    //set up screen states
    private fun processScreenState(state: ScreenStates<List<Hit>?>) {


        when (state) {
            is ScreenStates.Loading ->
                binding.progressBar.visibility = View.VISIBLE
            is ScreenStates.Success -> {
                binding.progressBar.visibility = View.GONE
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
                val view = binding.progressBar.rootView
                Snackbar.make(view, state.message!!, Snackbar.LENGTH_LONG).show()
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


        val action = fragment_homeDirections.actionFragmentHomeToRecipeFragment(image, ingredients)

        findNavController().navigate(action)


    }

    //convert image to bitmap
    private suspend fun getBitmap(imageUrl: String): Bitmap {
        val loading = ImageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data(imageUrl)
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable

        return (result as BitmapDrawable).bitmap

    }

    override fun onItemCLicked(position: Int) {
        var viewClicked = ObjectHit.hit[position]

        lifecycleScope.launch {
            val recipe = com.example.recipes.RoomDb.Recipe(
                0,
                viewClicked.recipe.label,
                getBitmap(viewClicked.recipe.image),
                viewClicked.recipe.ingredients
            )

            _dViewModel.insertRecipe(recipe)
        }

        Toast.makeText(context, "liked recipe at $position", Toast.LENGTH_SHORT).show()
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

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }
}


package com.fatihbaserpl.foodapplication.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fatihbaserpl.foodapplication.MainViewModel
import com.fatihbaserpl.foodapplication.R
import com.fatihbaserpl.foodapplication.RecipesViewModel
import com.fatihbaserpl.foodapplication.adapters.RecipesAdapter
import com.fatihbaserpl.foodapplication.databinding.FragmentRecipesBinding
import com.fatihbaserpl.foodapplication.utils.NetworkResult
import com.fatihbaserpl.foodapplication.utils.observeOnce
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel
    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy { RecipesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = viewModel
        setupRecyclerView()
        readDatabase()

        binding.recipesFab.setOnClickListener {
            //if (recipesViewModel.networkStatus) {
                findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheet)
          //  } else {
           //     recipesViewModel.showNetworkStatus()
           // }
        }
        return binding.root
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            viewModel.readRecipes.observeOnce(viewLifecycleOwner, { database ->
                if (database.isNotEmpty() ) {
                    Log.d("RecipesFragment", "readDatabase called!")
                    adapter.setData(database.first().foodRecipe)
                    hideShimmerEffect()
                } else {
                    requestApiData()
                }
            })
        }
    }






    private fun requestApiData() {
        Log.d("recipes", "Api")
        viewModel.getRecipes(recipesViewModel.applyQueries())
        viewModel.recipesResponse.observe(viewLifecycleOwner, { response ->
            when(response) {
                is NetworkResult.Loading -> showShimmerEffect()
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { adapter.setData(it) }
                }
                is NetworkResult.Error -> {

                    hideShimmerEffect()
                    loadDataFromCache()

                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun setupRecyclerView() {
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun showShimmerEffect() {
        binding.shimmerFrameLayout.startShimmer()
        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.recyclerview.visibility = View.GONE
    }
    private fun loadDataFromCache() {
        lifecycleScope.launch {
            viewModel.readRecipes.observe(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()) {
                    adapter.setData(database.first().foodRecipe)
                }
            })
        }
    }

    private fun hideShimmerEffect() {
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.recyclerview.visibility = View.VISIBLE
    }
    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}
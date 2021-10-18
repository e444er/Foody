package com.droidli.foody.ui.fragments.foodjoke

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.droidli.foody.R
import com.droidli.foody.data.database.entities.FoodJokeEntity
import com.droidli.foody.databinding.FoodJokeFragmentBinding
import com.droidli.foody.utils.Constants.Companion.API_KEY
import com.droidli.foody.utils.NetworkResult
import com.droidli.foody.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FoodJokeFragment : Fragment(R.layout.food_joke_fragment) {

    private val binding by viewBinding(FoodJokeFragmentBinding::bind)
    private val mainViewModel by viewModels<MainViewModel>()

    private var foodJoke = "No Food Joke"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        mainViewModel.getFoodJoke(API_KEY)
        responseViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.food_joke_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.share_food_joke_menu) {
            val shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT, foodJoke)
                this.type = "text/plain"
            }
            startActivity(shareIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun responseViewModel() {
        mainViewModel.foodJokeResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.progressBar.isVisible = false
                    binding.foodJokeCardView.isVisible = true
                    binding.foodJokeTextView.text = response.data?.text
                    if (response.data != null) {
                        foodJoke = response.data.text
                    }
                }
                is NetworkResult.Error -> {
                    loadDataFromCache()
                    Toast.makeText(requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.foodJokeCardView.isVisible = false
                }
            }
        })
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readFoodJokeEntity.observe(viewLifecycleOwner, { database ->
                noData(database)
                if (!database.isNullOrEmpty()) {
                    binding.foodJokeTextView.text = database[0].foodJoke.text
                    foodJoke = database[0].foodJoke.text
                }
            })
        }
    }

    private fun noData(database: List<FoodJokeEntity>?) {
        binding.foodJokeCardView.isVisible = true
        binding.progressBar.isVisible = false
        if (database.isNullOrEmpty()) {
            binding.foodJokeCardView.isVisible = false
            binding.foodJokeErrorTextView.isVisible = true
            binding.foodJokeErrorImageView.isVisible = true
        }
    }
}
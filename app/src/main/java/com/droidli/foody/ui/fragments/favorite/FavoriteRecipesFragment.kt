package com.droidli.foody.ui.fragments.favorite

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.droidli.foody.R
import com.droidli.foody.adapter.FavoriteRecipeAdapter
import com.droidli.foody.data.database.entities.FavoritesEntity
import com.droidli.foody.databinding.FavoriteRecipesFragmentBinding
import com.droidli.foody.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment(R.layout.favorite_recipes_fragment) {

    private val binding by viewBinding(FavoriteRecipesFragmentBinding::bind)
    private val mAdapter by lazy { FavoriteRecipeAdapter() }
    private val mainViewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        mainViewModel.readFavoritesEntity.observe(viewLifecycleOwner, {
            mAdapter.differFav.submitList(it)
            noData(it)
        })
    }

    private fun noData(favoritesEntity: List<FavoritesEntity>?) {
        if (favoritesEntity.isNullOrEmpty()) {
            binding.apply {
                favoriteRecyclerview.isVisible = false
                naDataImageView.isVisible = true
                noDataTextView.isVisible = true
            }
        } else {
            binding.apply {
                favoriteRecyclerview.isVisible = true
                naDataImageView.isVisible = false
                noDataTextView.isVisible = false
            }
        }
    }

    private fun setupRecyclerView() {
        binding.favoriteRecyclerview.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }
}
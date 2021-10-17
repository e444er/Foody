package com.droidli.foody.ui.fragments.detailfragment

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.droidli.foody.R
import com.droidli.foody.adapter.PagerAdapter
import com.droidli.foody.data.database.entities.FavoritesEntity
import com.droidli.foody.databinding.DetailFragmentBinding
import com.droidli.foody.ui.fragments.detailfragment.childfragment.IngredientsFragment
import com.droidli.foody.ui.fragments.detailfragment.childfragment.InstructionsFragment
import com.droidli.foody.ui.fragments.detailfragment.childfragment.OverviewFragment
import com.droidli.foody.utils.Constants.Companion.RECIPE_RESULT_KEY
import com.droidli.foody.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.detail_fragment) {

    private val binding by viewBinding(DetailFragmentBinding::bind)
    private val args by navArgs<DetailFragmentArgs>()
    private val mainViewModel: MainViewModel by viewModels()

    private var recipeSaved = false
    private var savedRecipeId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        val resultBundle = Bundle()
        resultBundle.putParcelable(RECIPE_RESULT_KEY, args.result)

        val _adapter = PagerAdapter(
            resultBundle,
            fragments,
            titles,
            activity?.supportFragmentManager!!
        )
        binding.viewPager.adapter = _adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.details_menu, menu)
        val menuItem = menu.findItem(R.id.save_to_favorite_menu)
        checkSaveRecipe(menuItem!!)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save_to_favorite_menu && !recipeSaved) {
            saveToFavorite(item)
        } else if (item.itemId == R.id.save_to_favorite_menu && recipeSaved) {
            removeFromFavorites(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkSaveRecipe(menuItem: MenuItem) {
        mainViewModel.readFavoritesEntity.observe(viewLifecycleOwner, { favoritesEntity ->
            try {
                for (savedRecipe in favoritesEntity) {
                    if (savedRecipe.result.id == args.result.id) {
                        changeMenuItemColor(menuItem, R.color.yellow)
                        savedRecipeId = savedRecipe.id
                        recipeSaved = true
                    } else {
                        changeMenuItemColor(menuItem, R.color.white)
                    }
                }
            } catch (e: Exception) {
                Log.d("DetailsFragment", e.message.toString())
            }
        })
    }

    private fun saveToFavorite(item: MenuItem) {
        val favoritesEntity =
            FavoritesEntity(
                0,
                args.result
            )
        mainViewModel.insertFavoritesEntity(favoritesEntity)
        changeMenuItemColor(item, R.color.yellow)
        showSnackBar("Save")
        recipeSaved = true
    }

    private fun removeFromFavorites(item: MenuItem) {
        val favoritesEntity =
            FavoritesEntity(
                savedRecipeId,
                args.result
            )
        mainViewModel.deleteFavoritesEntity(favoritesEntity)
        changeMenuItemColor(item, R.color.white)
        showSnackBar("Removed from Favorites.")
        recipeSaved = false
    }

    private fun showSnackBar(msg: String) {
        Snackbar.make(
            binding.appBar,
            msg,
            Snackbar.LENGTH_SHORT
        ).setAction("Okey") {}
            .show()
    }

    private fun changeMenuItemColor(item: MenuItem, yellow: Int) {
        item.icon.setTint(ContextCompat.getColor(requireContext(), yellow))
    }


}
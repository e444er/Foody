package com.droidli.foody.adapter

import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.droidli.foody.R
import com.droidli.foody.data.database.entities.FavoritesEntity
import com.droidli.foody.databinding.FavoriteRecipesRowLayoutBinding
import com.droidli.foody.ui.fragments.favorite.FavoriteRecipesFragmentDirections
import com.droidli.foody.utils.FavoriteDiffUtil
import com.droidli.foody.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import org.jsoup.Jsoup

class FavoriteRecipeAdapter(
    private val requireActivity: FragmentActivity,
    private val mainViewModel: MainViewModel,
) : RecyclerView.Adapter<FavoriteRecipeAdapter.MyViewHolder>(),
    ActionMode.Callback {

    private lateinit var mActionMode: ActionMode
    private lateinit var rootView: View

    val differFav = AsyncListDiffer(this, FavoriteDiffUtil())
    private var myViewHolder = arrayListOf<MyViewHolder>()
    private var selectedRecipes = arrayListOf<FavoritesEntity>()
    private var multiSelection = false

    class MyViewHolder(val binding: FavoriteRecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(FavoriteRecipesRowLayoutBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        myViewHolder.add(holder)
        val currentRecipe = differFav.currentList[position]

        rootView = holder.binding.root
        holder.binding.apply {
            favoriteTitleTextView.text = currentRecipe.result.title
            currentRecipe.result.summary.let {
                val summary = Jsoup.parse(it).text()
                favoriteDescriptionTextView.text = summary
            }
            favoriteHeartTextView.text = currentRecipe.result.aggregateLikes.toString()
            favoriteClockTextView.text = currentRecipe.result.readyInMinutes.toString()
            if (!currentRecipe.result.vegan) {
                favoriteLeafTextView.setTextColor(ContextCompat.getColor(holder.itemView.context,
                    R.color.Gray))
                favoriteLeafImageView.setColorFilter(ContextCompat.getColor(holder.itemView.context,
                    R.color.Gray))
            }else {
                favoriteLeafTextView.setTextColor(ContextCompat.getColor(holder.itemView.context,
                    R.color.green))
                favoriteLeafImageView.setColorFilter(ContextCompat.getColor(holder.itemView.context,
                    R.color.green))
            }
            Glide.with(root)
                .load(currentRecipe.result.image)
                .error(R.drawable.ic_terrain)
                .placeholder(R.drawable.ic_terrain)
                .into(favoriteRecipesImageView)
        }
        /**
         * Click Listener
         * */
        holder.binding.favoriteRecipeRowLayout.setOnClickListener {
            if (multiSelection) {
                applySelection(holder, currentRecipe)
            } else {
                val action = FavoriteRecipesFragmentDirections
                    .actionFavoriteRecipesFragmentToDetailFragment(currentRecipe.result)
                holder.itemView.findNavController().navigate(action)
            }
        }
        /**
         * Long Click Listener
         * */
        holder.binding.favoriteRecipeRowLayout.setOnLongClickListener {
            if (!multiSelection) {
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holder, currentRecipe)
                true
            } else {
                multiSelection = false
                false
            }
        }
    }

    private fun applySelection(holder: MyViewHolder, currentRecipe: FavoritesEntity) {
        if (selectedRecipes.contains(currentRecipe)) {
            selectedRecipes.remove(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackground, R.color.strokeColor)
            applyActionModeTitle()
        } else {
            selectedRecipes.add(currentRecipe)
            changeRecipeStyle(holder, R.color.cardview_light_background, R.color.purple_500)
            applyActionModeTitle()
        }
    }

    private fun changeRecipeStyle(holder: MyViewHolder, backgroundColor: Int, strokeColor: Int) {
        holder.binding.favoriteRecipeRowLayout.setBackgroundColor(
            ContextCompat.getColor(requireActivity, backgroundColor)
        )
        holder.binding.favoriteRowCardView.strokeColor =
            ContextCompat.getColor(requireActivity, strokeColor)
    }

    private fun applyActionModeTitle() {
        when (selectedRecipes.size) {
            0 -> {
                mActionMode.finish()
            }
            1 -> {
                mActionMode.title = "${selectedRecipes.size} item selected"
            }
            else -> {
                mActionMode.title = "${selectedRecipes.size} items selected"
            }
        }
    }

    override fun getItemCount() = differFav.currentList.size

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.favorite_recipe_menu, menu)
        mActionMode = mode!!
        applyStatusBarColor(R.color.favoriteStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        if (item?.itemId == R.id.delete_favorite_recipe_menu) {
            selectedRecipes.forEach {
                mainViewModel.deleteFavoritesEntity(it)
            }
            showSnackBar("${differFav.currentList.size} Recipe/s removed.")
            multiSelection = false
            selectedRecipes.clear()
            mode?.finish()
        }
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        myViewHolder.forEach { holder ->
            changeRecipeStyle(holder, R.color.cardBackground, R.color.strokeColor)
        }
        multiSelection = false
        selectedRecipes.clear()
        applyStatusBarColor(R.color.statusBarColor)
    }

    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor =
            ContextCompat.getColor(requireActivity, color)
    }

    private fun showSnackBar(msg: String) {
        Snackbar.make(
            rootView,
            msg,
            Snackbar.LENGTH_SHORT
        ).setAction("Okey") {}
            .show()
    }

    fun clearActionMode() {
        if (this::mActionMode.isInitialized) {
            mActionMode.finish()
        }
    }
}
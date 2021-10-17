package com.droidli.foody.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.droidli.foody.R
import com.droidli.foody.databinding.FavoriteRecipesRowLayoutBinding
import com.droidli.foody.utils.FavoriteDiffUtil
import org.jsoup.Jsoup

class FavoriteRecipeAdapter : RecyclerView.Adapter<FavoriteRecipeAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: FavoriteRecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    val differFav = AsyncListDiffer(this, FavoriteDiffUtil())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(FavoriteRecipesRowLayoutBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = differFav.currentList[position]

        holder.binding.apply {
            favoriteTitleTextView.text = currentRecipe.result.title
            currentRecipe.result.summary.let {
                val summary = Jsoup.parse(it).text()
                favoriteDescriptionTextView.text = summary
            }
            favoriteHeartTextView.text = currentRecipe.result.aggregateLikes.toString()
            favoriteClockTextView.text = currentRecipe.result.readyInMinutes.toString()
            if (currentRecipe.result.vegan == true) {
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
//        holder.binding.favoriteRecipeRowLayout.setOnClickListener {
//            val action = RecipesFragmentDirections
//                .actionRecipesFragmentToDetailFragment(currentRecipe)
//            holder.itemView.findNavController().navigate(action)
//        }
//        }
    }

    override fun getItemCount() = differFav.currentList.size


}
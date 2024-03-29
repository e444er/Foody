package com.droidli.foody.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.droidli.foody.R
import com.droidli.foody.databinding.RecipesRowLayoutBinding
import com.droidli.foody.ui.fragments.recipe.RecipesFragmentDirections
import com.droidli.foody.utils.RecipesDiffUtil
import org.jsoup.Jsoup

class RecipeAdapter : RecyclerView.Adapter<RecipeAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: RecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    val differ = AsyncListDiffer(this, RecipesDiffUtil())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(RecipesRowLayoutBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = differ.currentList[position]
        holder.binding.apply {
            titleTextView.text = currentRecipe.title
            currentRecipe.summary.let{
                val summary = Jsoup.parse(it).text()
                descriptionTextView.text = summary
            }
            heartTextView.text = currentRecipe.aggregateLikes.toString()
            clockTextView.text = currentRecipe.readyInMinutes.toString()
            if (!currentRecipe.vegan) {
                leafTextView.setTextColor(ContextCompat.getColor(holder.itemView.context,
                    R.color.Gray))
                leafImageView.setColorFilter(ContextCompat.getColor(holder.itemView.context,
                    R.color.Gray))
            }else {
                leafTextView.setTextColor(ContextCompat.getColor(holder.itemView.context,
                    R.color.green))
                leafImageView.setColorFilter(ContextCompat.getColor(holder.itemView.context,
                    R.color.green))
            }

            Glide.with(root)
                .load(currentRecipe.image)
                .error(R.drawable.ic_terrain)
                .placeholder(R.drawable.ic_terrain)
                .into(recipesImageView)
        }
        /**
         * Click Listener
         * */
        holder.binding.recipeRowLayout.setOnClickListener {
            val action = RecipesFragmentDirections
                .actionRecipesFragmentToDetailFragment(currentRecipe)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount() = differ.currentList.size


}
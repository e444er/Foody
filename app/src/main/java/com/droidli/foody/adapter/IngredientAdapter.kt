package com.droidli.foody.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.droidli.foody.R
import com.droidli.foody.databinding.IngredientRowLayoutBinding
import com.droidli.foody.utils.Constants.Companion.BASE_IMAGE_URL
import com.droidli.foody.utils.IngredientDiffUtil

class IngredientAdapter : RecyclerView.Adapter<IngredientAdapter.IngreViewHolder>() {

    class IngreViewHolder(val binding: IngredientRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    val differIn = AsyncListDiffer(this, IngredientDiffUtil())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngreViewHolder {
        return IngreViewHolder(IngredientRowLayoutBinding.inflate(LayoutInflater
            .from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: IngreViewHolder, position: Int) {
        val currentIngredient = differIn.currentList[position]
        holder.binding.apply {
            Glide.with(root)
                .load(BASE_IMAGE_URL + currentIngredient.image)
                .error(R.drawable.ic_terrain)
                .placeholder(R.drawable.ic_terrain)
                .into(ingredientImageView)
            ingredietName.text = currentIngredient.name.capitalize()
            ingredientAmount.text = currentIngredient.amount.toString()
            ingredientUnit.text = currentIngredient.unit
            ingredientConsistency.text = currentIngredient.consistency
            ingredientOriginal.text = currentIngredient.original
        }
    }

    override fun getItemCount() = differIn.currentList.size

}
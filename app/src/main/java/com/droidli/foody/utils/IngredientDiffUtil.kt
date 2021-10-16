package com.droidli.foody.utils

import androidx.recyclerview.widget.DiffUtil
import com.droidli.foody.models.ExtendedIngredient

class IngredientDiffUtil : DiffUtil.ItemCallback<ExtendedIngredient>() {
    override fun areItemsTheSame(
        oldItem: ExtendedIngredient,
        newItem: ExtendedIngredient,
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: ExtendedIngredient,
        newItem: ExtendedIngredient,
    ): Boolean {
        return oldItem == newItem
    }
}
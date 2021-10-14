package com.droidli.foody.utils

import androidx.recyclerview.widget.DiffUtil
import com.droidli.foody.models.Result

class RecipesDiffUtil : DiffUtil.ItemCallback<Result>() {
    override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem == newItem
    }
}
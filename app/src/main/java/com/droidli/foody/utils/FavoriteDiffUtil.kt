package com.droidli.foody.utils

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.droidli.foody.data.database.entities.FavoritesEntity

class FavoriteDiffUtil : DiffUtil.ItemCallback<FavoritesEntity>() {
    override fun areItemsTheSame(oldItem: FavoritesEntity, newItem: FavoritesEntity): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: FavoritesEntity, newItem: FavoritesEntity): Boolean {
        return oldItem == newItem
    }

}
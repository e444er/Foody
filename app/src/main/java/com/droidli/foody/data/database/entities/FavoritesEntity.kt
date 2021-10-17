package com.droidli.foody.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.droidli.foody.models.Result
import com.droidli.foody.utils.Constants.Companion.FAVORITE_RECIPES_TABLE

@Entity(tableName = FAVORITE_RECIPES_TABLE)
class FavoritesEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)
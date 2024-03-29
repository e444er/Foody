package com.droidli.foody.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.droidli.foody.models.FoodRecipe
import com.droidli.foody.utils.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe,
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}

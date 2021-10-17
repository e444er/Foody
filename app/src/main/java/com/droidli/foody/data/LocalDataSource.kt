package com.droidli.foody.data

import com.droidli.foody.data.database.RecipeDao
import com.droidli.foody.data.database.entities.FavoritesEntity
import com.droidli.foody.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipeDao: RecipeDao,
) {

    fun readRecipes(): Flow<List<RecipesEntity>> {
        return recipeDao.readRecipes()
    }

    fun readFavoriteEntity(): Flow<List<FavoritesEntity>> {
        return recipeDao.readFavoritesEntity()
    }

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipeDao.insertRecipes(recipesEntity)
    }

    suspend fun insertFavoritesEntity(favoritesEntity: FavoritesEntity) {
        recipeDao.insertFavoritesEntity(favoritesEntity)
    }

    suspend fun deleteFavoritesEntity(favoritesEntity: FavoritesEntity) {
        recipeDao.deleteFavoritesEntity(favoritesEntity)
    }

    suspend fun deleteAllFavoritesEntity() {
        recipeDao.deleteAllFavoritesEntity()
    }
}
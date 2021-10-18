package com.droidli.foody.data.database

import androidx.room.*
import com.droidli.foody.data.database.entities.FavoritesEntity
import com.droidli.foody.data.database.entities.FoodJokeEntity
import com.droidli.foody.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)

    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun readRecipes(): Flow<List<RecipesEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoritesEntity(favoritesEntity: FavoritesEntity)

    @Query("SELECT * FROM favorite_recipes_table ORDER BY id ASC")
    fun readFavoritesEntity(): Flow<List<FavoritesEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFoodJokeEntity(foodJokeEntity: FoodJokeEntity)

    @Query("SELECT * FROM food_joke_table ORDER BY id ASC")
    fun readFoodJokeEntity(): Flow<List<FoodJokeEntity>>

    @Delete
    suspend fun deleteFavoritesEntity(favoritesEntity: FavoritesEntity)

    @Query("DELETE FROM favorite_recipes_table")
    suspend fun deleteAllFavoritesEntity()
}
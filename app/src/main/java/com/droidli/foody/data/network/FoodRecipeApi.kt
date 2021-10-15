package com.droidli.foody.data.network

import com.droidli.foody.models.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FoodRecipeApi {

    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        @QueryMap queries: Map<String, String>,
    ): Response<FoodRecipe>


    @GET("/recipes/complexSearch")
    suspend fun searchRecipe(
        @QueryMap searchQuery: Map<String, String>,
    ): Response<FoodRecipe>

}
//https://api.spoonacular.com/recipes/complexSearch
// ?number=1&apiKey=1ac5a43d6d7742adb2e726a26104071a
// &type=drink&diet=vegan&addRecipeInformation=true
// &fillIngredients=true
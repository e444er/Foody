package com.droidli.foody.utils

class Constants {

    companion object {
        const val BASE_URL = "https://api.spoonacular.com"
        const val BASE_IMAGE_URL = "https://spoonacular.com/cdn/ingredients_100x100/"
        const val API_KEY = "1ac5a43d6d7742adb2e726a26104071a"

        const val RECIPE_RESULT_KEY = "recipeBundle"

        // API Query Keys
        const val QUERY_SEARCH = "query"
        const val QUERIES_NUMBER = "number"
        const val QUERIES_APIKEY = "apiKey"
        const val QUERIES_TYPE = "type"
        const val QUERIES_DIET = "diet"
        const val QUERIES_ADDRECIPEINFO = "addRecipeInformation"
        const val QUERIES_FILLING = "fillIngredients"

        //ROOM Database
        const val DATABASE_NAME = "recipes_database"
        const val RECIPES_TABLE = "recipes_table"
        const val FOOD_JOKE_TABLE = "food_joke_table"
        const val FAVORITE_RECIPES_TABLE = "favorite_recipes_table"

        // Bottom Sheet and Preferences
        const val DEFAULT_RECIPES_NUMBER = "50"
        const val DEFAULT_MEAL_TYPE = "main course"
        const val DEFAULT_DIET_TYPE = "gluten free"

        const val PREFERENCES_NAME = "foody_preferences"
        const val PREFERENCES_MEAL_TYPE = "mealType"
        const val PREFERENCES_MEAL_TYPE_ID = "mealTypeId"
        const val PREFERENCES_DIET_TYPE = "dietType"
        const val PREFERENCES_DIET_TYPE_ID = "dietTypeId"
        const val PREFERENCES_BACK_ONLINE = "backOnline"
    }
}
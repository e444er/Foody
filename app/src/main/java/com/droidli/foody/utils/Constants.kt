package com.droidli.foody.utils

class Constants {

    companion object {
        const val BASE_URL = "https://api.spoonacular.com"
        const val API_KEY = "1ac5a43d6d7742adb2e726a26104071a"

        // API Query Keys
        const val QUERIES_NUMBER = "number"
        const val QUERIES_APIKEY = "apiKey"
        const val QUERIES_TYPE = "type"
        const val QUERIES_DIET = "diet"
        const val QUERIES_ADDRECIPEINFO = "addRecipeInformation"
        const val QUERIES_FILLING = "fillIngredients"

        //ROOM Database
        const val DATABASE_NAME = "recipes_database"
        const val RECIPES_TABLE = "recipes_table"
    }
}
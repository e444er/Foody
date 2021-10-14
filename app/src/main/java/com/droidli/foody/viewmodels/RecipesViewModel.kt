package com.droidli.foody.viewmodels

import androidx.lifecycle.ViewModel
import com.droidli.foody.utils.Constants.Companion.API_KEY
import com.droidli.foody.utils.Constants.Companion.QUERIES_ADDRECIPEINFO
import com.droidli.foody.utils.Constants.Companion.QUERIES_APIKEY
import com.droidli.foody.utils.Constants.Companion.QUERIES_DIET
import com.droidli.foody.utils.Constants.Companion.QUERIES_FILLING
import com.droidli.foody.utils.Constants.Companion.QUERIES_NUMBER
import com.droidli.foody.utils.Constants.Companion.QUERIES_TYPE

class RecipesViewModel : ViewModel() {

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries[QUERIES_NUMBER] = "50"
        queries[QUERIES_APIKEY] = API_KEY
        queries[QUERIES_TYPE] = "snack"
        queries[QUERIES_DIET] = "vegan"
        queries[QUERIES_ADDRECIPEINFO] = "true"
        queries[QUERIES_FILLING] = "true"

        return queries
    }
}
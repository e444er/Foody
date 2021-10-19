package com.droidli.foody.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.droidli.foody.data.DataStoreRepository
import com.droidli.foody.utils.Constants.Companion.API_KEY
import com.droidli.foody.utils.Constants.Companion.DEFAULT_DIET_TYPE
import com.droidli.foody.utils.Constants.Companion.DEFAULT_MEAL_TYPE
import com.droidli.foody.utils.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.droidli.foody.utils.Constants.Companion.QUERIES_ADDRECIPEINFO
import com.droidli.foody.utils.Constants.Companion.QUERIES_APIKEY
import com.droidli.foody.utils.Constants.Companion.QUERIES_DIET
import com.droidli.foody.utils.Constants.Companion.QUERIES_FILLING
import com.droidli.foody.utils.Constants.Companion.QUERIES_NUMBER
import com.droidli.foody.utils.Constants.Companion.QUERIES_TYPE
import com.droidli.foody.utils.Constants.Companion.QUERY_SEARCH
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    @ApplicationContext val context: Context,
) : ViewModel() {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    var networkStatus = false
    var backOnline = false

    val readMealAndDietType = dataStoreRepository.readMealAndDietType
    val readBackOnline = dataStoreRepository.readBackOnline.asLiveData()

    fun saveMealAndDietType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int,
    ) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(
                mealType,
                mealTypeId,
                dietType,
                dietTypeId)
        }

    fun saveBackOnline(backOnline: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveBackOnline(backOnline)
        }

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        viewModelScope.launch {
            readMealAndDietType.collect { value ->
                mealType = value.selectedMealType
                dietType = value.selectedDietType
            }
        }

        queries[QUERIES_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERIES_APIKEY] = API_KEY
        queries[QUERIES_TYPE] = mealType
        queries[QUERIES_DIET] = dietType
        queries[QUERIES_ADDRECIPEINFO] = "true"
        queries[QUERIES_FILLING] = "true"

        return queries
    }

    fun applySearchQuery(searchQuery: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_SEARCH] = searchQuery
        queries[QUERIES_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERIES_APIKEY] = API_KEY
        queries[QUERIES_ADDRECIPEINFO] = "true"
        queries[QUERIES_FILLING] = "true"
        return queries
    }


    fun showNetworkStatus() {
        if (!networkStatus) {
            Toast.makeText(context, "No Internet Connection.", Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        } else if (networkStatus) {
            if (backOnline) {
                Toast.makeText(context, "We're back online.", Toast.LENGTH_SHORT).show()
                saveBackOnline(false)
            }
        }
    }
}
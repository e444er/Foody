package com.droidli.foody.viewmodels

import androidx.lifecycle.ViewModel
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    val readMealAndDietType = dataStoreRepository.readMealAndDietType

    fun saveMealAndDietType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int, ) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(
                mealType,
                mealTypeId,
                dietType,
                dietTypeId)
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
}
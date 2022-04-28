package com.fatihbaserpl.foodapplication

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fatihbaserpl.foodapplication.data.DataStoreRepository
import com.fatihbaserpl.foodapplication.utils.Constants.Companion.API_KEY
import com.fatihbaserpl.foodapplication.utils.Constants.Companion.DEFAULT_DIET_TYPE
import com.fatihbaserpl.foodapplication.utils.Constants.Companion.DEFAULT_MEAL_TYPE
import com.fatihbaserpl.foodapplication.utils.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.fatihbaserpl.foodapplication.utils.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.fatihbaserpl.foodapplication.utils.Constants.Companion.QUERY_API_KEY
import com.fatihbaserpl.foodapplication.utils.Constants.Companion.QUERY_DIET
import com.fatihbaserpl.foodapplication.utils.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.fatihbaserpl.foodapplication.utils.Constants.Companion.QUERY_NUMBER
import com.fatihbaserpl.foodapplication.utils.Constants.Companion.QUERY_TYPE
import dagger.hilt.android.AndroidEntryPoint

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = DEFAULT_MEAL_TYPE
        queries[QUERY_DIET] = DEFAULT_DIET_TYPE
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"





        return queries
    }


}
package com.fatihbaserpl.foodapplication.data

import com.fatihbaserpl.foodapplication.data.network.FoodRecipesApi
import com.fatihbaserpl.foodapplication.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {
    suspend fun getRecipes(queries: Map<String,String>):Response<FoodRecipe>{
        return foodRecipesApi.getRecipes(queries)
    }
}

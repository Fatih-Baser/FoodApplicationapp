package com.fatihbaserpl.foodapplication.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.fatihbaserpl.foodapplication.data.entities.RecipesEntity
import com.fatihbaserpl.foodapplication.models.FoodRecipe
import com.fatihbaserpl.foodapplication.utils.NetworkResult

class RecipesBinding {

    companion object {

        @BindingAdapter("readApiResponse","readDatabase",requireAll = true)
        @JvmStatic
        fun handleReadDataErrors(
            view: View,
            apiResponse: NetworkResult<FoodRecipe>?,
            database: List<RecipesEntity>?
        ){
            when (view){
                is ImageView ->{
                    view.isVisible = apiResponse is NetworkResult.Error && database.isNullOrEmpty()
                }
                is TextView ->{
                    view.isVisible = apiResponse is NetworkResult.Error && database.isNullOrEmpty()
                    view.text = apiResponse?.message.toString()
                }
            }
        }
    }

}
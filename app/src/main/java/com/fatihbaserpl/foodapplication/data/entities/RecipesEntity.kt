package com.fatihbaserpl.foodapplication.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fatihbaserpl.foodapplication.models.FoodRecipe
import com.fatihbaserpl.foodapplication.utils.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
)  {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}
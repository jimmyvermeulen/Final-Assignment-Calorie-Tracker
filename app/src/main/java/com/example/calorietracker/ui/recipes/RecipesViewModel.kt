package com.example.calorietracker.ui.recipes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.calorietracker.database.CalorieRepository
import com.example.calorietracker.model.Ingredient
import com.example.calorietracker.model.Recipe
import com.example.calorietracker.model.RecipeWithIngredients
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipesViewModel(application: Application) : AndroidViewModel(application) {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val calorieRepository = CalorieRepository(application.applicationContext)

    val recipesWithIngredients: LiveData<List<RecipeWithIngredients>> = calorieRepository.getAllRecipesWithIngredients()


    fun insertRecipeWithIngredients(recipeWithIngredients: RecipeWithIngredients) {
        ioScope.launch {
            calorieRepository.insertRecipeWithIngredients(recipeWithIngredients)
        }
    }

    fun deleteRecipe(recipe: Recipe){
        ioScope.launch {
            calorieRepository.deleteRecipe(recipe)
        }
    }

    fun updateRecipeWithIngredients(recipeWithIngredients: RecipeWithIngredients){
        ioScope.launch {
            calorieRepository.updateRecipeWithIngredients(recipeWithIngredients)
        }
    }
}
package com.example.calorietracker.ui.ingredients

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.calorietracker.database.CalorieRepository
import com.example.calorietracker.model.Ingredient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IngredientsViewModel(application: Application) : AndroidViewModel(application) {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val calorieRepository = CalorieRepository(application.applicationContext)

    val ingredients: LiveData<List<Ingredient>> = calorieRepository.getAllIngredients()

    fun insertIngredient(ingredient: Ingredient) {
        ioScope.launch {
            calorieRepository.insertIngredient(ingredient)
        }
    }

    fun updateIngredient(ingredient: Ingredient) {
        ioScope.launch {
            calorieRepository.updateIngredient(ingredient)
        }
    }

    fun deleteIngredient(ingredient: Ingredient) {
        ioScope.launch {
            calorieRepository.deleteIngredient(ingredient)
        }
    }
}
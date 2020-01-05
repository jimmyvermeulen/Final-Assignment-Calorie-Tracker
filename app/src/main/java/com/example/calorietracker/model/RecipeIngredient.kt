package com.example.calorietracker.model

import androidx.room.Entity

@Entity(primaryKeys = ["recipeId", "ingredientId"], tableName = "recipeIngredientTable")
data class RecipeIngredient(
    val recipeId: Long,
    val ingredientId: Long,
    val amount: Double
)

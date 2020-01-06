package com.example.calorietracker.model

import androidx.room.Entity

@Entity(primaryKeys = ["recipeId", "quantityId"], tableName = "recipeQuantityTable")
data class RecipeQuantity(
    val recipeId: Long,
    val quantityId: Long
)

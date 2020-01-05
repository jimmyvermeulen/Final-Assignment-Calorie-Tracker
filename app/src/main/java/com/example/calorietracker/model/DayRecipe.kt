package com.example.calorietracker.model

import androidx.room.Entity

@Entity(primaryKeys = ["dayId", "recipeId"], tableName = "dayRecipeTable")
data class DayRecipe(
    val dayId: Long,
    val recipeId: Long

)

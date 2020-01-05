package com.example.calorietracker.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.calorietracker.model.Recipe
import com.example.calorietracker.model.RecipeWithIngredients

@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipeTable")
    fun getAllRecipes(): LiveData<List<Recipe>>

    @Insert
    fun insertRecipe(recipe: Recipe) : Long

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("DELETE FROM recipeTable")
    suspend fun deleteAllRecipes()

    @Transaction
    @Query("SELECT * FROM recipeTable")
    fun getAllRecipesWithIngredients(): LiveData<List<RecipeWithIngredients>>


}
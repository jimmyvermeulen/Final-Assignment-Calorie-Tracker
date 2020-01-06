package com.example.calorietracker.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.calorietracker.model.RecipeIngredient
import com.example.calorietracker.model.RecipeQuantity
import com.example.calorietracker.model.RecipeWithIngredients

@Dao
interface RecipeIngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipeIngredient(recipeIngredient: RecipeIngredient)


    @Delete
    suspend fun deleteRecipeIngredient(recipeIngredient: RecipeIngredient)

    @Transaction
    @Query("SELECT * FROM recipeTable")
    fun getAllRecipesWithIngredients(): LiveData<List<RecipeWithIngredients>>

    @Query("DELETE FROM recipeIngredientTable WHERE recipeId = :recipeId")
    suspend fun deleteRecipeIngredientsWithRecipe(recipeId: Long)

    @Query("DELETE FROM recipeIngredientTable WHERE ingredientId = :ingredientId")
    suspend fun deleteRecipeIngredientsWithIngredient(ingredientId: Long)
}
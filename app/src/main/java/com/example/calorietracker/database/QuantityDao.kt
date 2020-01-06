package com.example.calorietracker.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.calorietracker.model.Quantity
import com.example.calorietracker.model.RecipeQuantity

@Dao
interface QuantityDao {

    @Query("SELECT * FROM quantityTable")
    fun getAllQuantities(): LiveData<List<Quantity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuantity(quantity: Quantity) : Long

    @Delete
    suspend fun deleteIngredient(quantity: Quantity)

    @Query("DELETE FROM ingredientTable")
    suspend fun deleteAllIngredients()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipeQuantity(recipeQuantity: RecipeQuantity)

    @Query("DELETE FROM recipeQuantityTable WHERE recipeId = :recipeId")
    suspend fun deleteRecipeQuantitiesWithRecipe(recipeId: Long)

}
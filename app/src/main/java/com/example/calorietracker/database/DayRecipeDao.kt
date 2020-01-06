package com.example.calorietracker.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.calorietracker.model.DayRecipe
import com.example.calorietracker.model.DayWithRecipes
import com.example.calorietracker.model.RecipeWithIngredients
import java.util.*

@Dao
interface DayRecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDayRecipe(dayRecipe: DayRecipe)

    @Transaction
    @Query("SELECT * FROM dayTable WHERE date = :date")
    fun getDayWithRecipes(date: Date): LiveData<DayWithRecipes>

    @Transaction
    @Query("SELECT r.* FROM dayTable d INNER JOIN dayRecipeTable dr ON d.dayId = dr.dayId INNER JOIN recipeTable r ON dr.recipeId = r.recipeId WHERE d.dayId = :dayId")
    fun getRecipeIngredientsFromDay(dayId: Long): LiveData<List<RecipeWithIngredients>>

    @Transaction
    @Query("SELECT * FROM dayTable")
    fun getAllDaysWithRecipes(): LiveData<List<DayWithRecipes>>

    @Query("DELETE FROM dayRecipeTable WHERE recipeId = :recipeId")
    suspend fun deleteDayRecipesWithRecipe(recipeId: Long)




}
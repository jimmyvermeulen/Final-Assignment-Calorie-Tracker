package com.example.calorietracker.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.calorietracker.model.DayRecipe
import com.example.calorietracker.model.DayWithRecipes
import java.util.*

@Dao
interface DayRecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDayRecipe(dayRecipe: DayRecipe)

    @Transaction
    @Query("SELECT * FROM dayTable WHERE dayId = :dayId")
    fun getDayWithRecipes(dayId: Long): LiveData<DayWithRecipes>

    @Query("DELETE FROM dayRecipeTable WHERE dayId = :dayId")
    suspend fun deleteDayRecipesWithDay(dayId: Long)

    @Query("DELETE FROM dayRecipeTable WHERE recipeId = :recipeId")
    suspend fun deleteDayRecipesWithRecipe(recipeId: Long)


}
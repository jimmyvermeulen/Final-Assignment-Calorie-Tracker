package com.example.calorietracker.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.calorietracker.model.Day
import com.example.calorietracker.model.RecipeWithIngredients
import java.util.*

@Dao
interface DayDao {

    @Query("SELECT * FROM dayTable")
    fun getAllDays(): LiveData<List<Day>>

    @Query("SELECT * FROM dayTable WHERE dayId = :dayId")
    fun getDay(dayId: Long): LiveData<Day>

    @Query("SELECT r.* FROM dayTable d INNER JOIN dayRecipeTable dr ON d.dayId=dr.dayId INNER JOIN recipeTable r ON dr.recipeId=r.recipeId WHERE d.dayId=:dayId")
    fun getRecipesWithIngredientsForDay(dayId: Long) : LiveData<List<RecipeWithIngredients>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDay(day: Day): Long

    @Delete
    suspend fun deleteDay(day: Day)

    @Query("DELETE FROM dayTable")
    suspend fun deleteAllDays()

}
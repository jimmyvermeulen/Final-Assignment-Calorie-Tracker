package com.example.calorietracker.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.calorietracker.model.*
import java.util.*

public class CalorieRepository(context: Context) {

    private var recipeDao : RecipeDao
    private var ingredientDao : IngredientDao
    private var dayDao : DayDao
    private var recipeIngredientDao : RecipeIngredientDao
    private var dayRecipeDao : DayRecipeDao

    init {
        val calorieRoomDatabase = CalorieRoomDatabase.getDatabase(context)
        recipeDao = calorieRoomDatabase!!.recipeDao()
        ingredientDao = calorieRoomDatabase!!.ingredientDao()
        dayDao = calorieRoomDatabase!!.dayDao()
        recipeIngredientDao = calorieRoomDatabase!!.recipeIngredientDao()
        dayRecipeDao = calorieRoomDatabase!!.dayRecipeDao()
    }

    //CREATE

    fun insertRecipeWithIngredients(recipeWithIngredients: RecipeWithIngredients, ingredientAmounts: List<Double>){
        var recipeId : Long = recipeDao.insertRecipe(recipeWithIngredients.recipe)
        for(i in recipeWithIngredients.ingredients.indices){
            recipeIngredientDao.insertRecipeIngredient(
                RecipeIngredient(
                    recipeId,
                    recipeWithIngredients.ingredients[i].ingredientId!!,
                    ingredientAmounts[i]
                )
            )
        }
    }

    fun insertIngredient(ingredient: Ingredient){
        ingredientDao.insertIngredient(ingredient)
    }

    //READ
    fun getAllRecipesWithIngredients(): LiveData<List<RecipeWithIngredients>>{
        return recipeDao.getAllRecipesWithIngredients()
    }

    fun getAllRecipes(): LiveData<List<Recipe>>{
        return recipeDao.getAllRecipes()
    }


    fun getAllIngredients(): LiveData<List<Ingredient>>{
        return ingredientDao.getAllIngredients()
    }

    fun getDayFromDate(date: Date): LiveData<Day>{
        return dayDao.getDay(date)
    }

    //UPDATE


    //DELETE
    suspend fun deleteIngredient(ingredient: Ingredient){
        recipeIngredientDao.deleteRecipeIngredientsWithIngredient(ingredient.ingredientId!!)
        ingredientDao.deleteIngredient(ingredient)
    }

    suspend fun deleteDay(day: Day){
        dayRecipeDao.deleteDayRecipesWithDay(day.dayId!!)
        dayDao.deleteDay(day)
    }

    suspend fun deleteRecipe(recipe: Recipe){
        recipeIngredientDao.deleteRecipeIngredientsWithRecipe(recipe.recipeId!!)
        dayRecipeDao.deleteDayRecipesWithRecipe(recipe.recipeId!!)
        recipeDao.deleteRecipe(recipe)
    }

}
package com.example.calorietracker.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.calorietracker.ui.api.FoodApi
import com.example.calorietracker.ui.api.FoodApiService
import com.example.calorietracker.model.*
import java.util.*

public class CalorieRepository(context: Context) {

    private var recipeDao : RecipeDao
    private var ingredientDao : IngredientDao
    private var dayDao : DayDao
    private var recipeIngredientDao : RecipeIngredientDao
    private var dayRecipeDao : DayRecipeDao
    private val foodApi: FoodApiService = FoodApi.createApi()

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

    fun insertDay(day: Day){
        dayDao.insertDay(day)
    }

    //READ
    fun getAllRecipesWithIngredients(): LiveData<List<RecipeWithIngredients>>{
        return recipeDao.getAllRecipesWithIngredients()
    }

    fun getRecipesWithIngredientsFromRecipes(recipes: List<Long>): LiveData<List<RecipeWithIngredients>>{
        return recipeDao.getRecipesWithIngredientsFromRecipess(recipes)
    }

    fun getAllRecipes(): LiveData<List<Recipe>>{
        return recipeDao.getAllRecipes()
    }


    fun getAllIngredients(): LiveData<List<Ingredient>>{
        return ingredientDao.getAllIngredients()
    }

    fun getRecipesFromDate(date: Date): LiveData<DayWithRecipes>{
        var day : Day = dayDao.getDay(date).value!!
        return dayRecipeDao.getDayWithRecipes(day.dayId!!)
    }

    fun getDayWithRecipesFromDate(dayId: Long): LiveData<DayWithRecipes>{
        return dayRecipeDao.getDayWithRecipes(dayId)
    }

    fun getDayFromDate(date: Date): LiveData<Day> {
        return dayDao.getDay(date)
    }

    fun getAllDaysWithRecipes():LiveData<List<DayWithRecipes>>{
        return dayRecipeDao.getAllDaysWithRecipes()
    }

    fun getFoodList(ingredientName: String) = foodApi.getFoodList(ingredientName)

    //UPDATE


    //DELETE
    suspend fun deleteIngredient(ingredient: Ingredient){
        recipeIngredientDao.deleteRecipeIngredientsWithIngredient(ingredient.ingredientId!!)
        ingredientDao.deleteIngredient(ingredient)
    }

    suspend fun deleteRecipe(recipe: Recipe){
        recipeIngredientDao.deleteRecipeIngredientsWithRecipe(recipe.recipeId!!)
        dayRecipeDao.deleteDayRecipesWithRecipe(recipe.recipeId!!)
        recipeDao.deleteRecipe(recipe)
    }

}
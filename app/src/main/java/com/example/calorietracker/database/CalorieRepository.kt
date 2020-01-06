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
    private var quantityDao : QuantityDao
    private val foodApi: FoodApiService = FoodApi.createApi()

    init {
        val calorieRoomDatabase = CalorieRoomDatabase.getDatabase(context)
        recipeDao = calorieRoomDatabase!!.recipeDao()
        ingredientDao = calorieRoomDatabase!!.ingredientDao()
        dayDao = calorieRoomDatabase!!.dayDao()
        recipeIngredientDao = calorieRoomDatabase!!.recipeIngredientDao()
        dayRecipeDao = calorieRoomDatabase!!.dayRecipeDao()
        quantityDao = calorieRoomDatabase!!.quantityDao()
    }

    //CREATE

    fun insertRecipeWithIngredients(recipeWithIngredients: RecipeWithIngredients){
        val recipeId : Long = recipeDao.insertRecipe(recipeWithIngredients.recipe)
        for(ingredient in recipeWithIngredients.ingredients){
            recipeIngredientDao.insertRecipeIngredient(RecipeIngredient(recipeId, ingredient.ingredientId!!))
        }
        for(quantity in recipeWithIngredients.quantities){
            val quantityId : Long = quantityDao.insertQuantity(quantity)
            quantityDao.insertRecipeQuantity(RecipeQuantity(recipeId, quantityId))
        }
    }

    fun insertIngredient(ingredient: Ingredient){
        ingredientDao.insertIngredient(ingredient)
    }

    fun insertDay(day: Day) : Long{
        return dayDao.insertDay(day)
    }

    //READ
    fun getAllRecipesWithIngredients(): LiveData<List<RecipeWithIngredients>>{
        return recipeDao.getAllRecipesWithIngredients()
    }

    fun getRecipesWithIngredientsFromRecipes(recipes: List<Long>): LiveData<List<RecipeWithIngredients>>{
        return recipeDao.getRecipesWithIngredientsFromRecipes(recipes)
    }

    fun getAllRecipes(): LiveData<List<Recipe>>{
        return recipeDao.getAllRecipes()
    }


    fun getAllIngredients(): LiveData<List<Ingredient>>{
        return ingredientDao.getAllIngredients()
    }

    fun getDayWithRecipesFromDay(date: Date): LiveData<DayWithRecipes>{
        return dayRecipeDao.getDayWithRecipes(date)
    }

    fun getAllDaysWithRecipes():LiveData<List<DayWithRecipes>>{
        return dayRecipeDao.getAllDaysWithRecipes()
    }

    fun getFoodList(ingredientName: String) = foodApi.getFoodList(ingredientName)

    //UPDATE
    fun updateIngredient(ingredient: Ingredient){
        ingredientDao.updateIngredient(ingredient)
    }

    suspend fun updateRecipeWithIngredients(recipeWithIngredients: RecipeWithIngredients){
        recipeDao.updateRecipe(recipeWithIngredients.recipe)
        //Update by remove all and add new
        recipeIngredientDao.deleteRecipeIngredientsWithIngredient(recipeWithIngredients.recipe.recipeId!!)
        for(ingredient in recipeWithIngredients.ingredients){
            recipeIngredientDao.insertRecipeIngredient(RecipeIngredient(recipeWithIngredients.recipe.recipeId!!, ingredient.ingredientId!!))
        }
        quantityDao.deleteRecipeQuantitiesWithRecipe(recipeWithIngredients.recipe.recipeId!!)
        for(quantity in recipeWithIngredients.quantities){
            val quantityId : Long = quantityDao.insertQuantity(quantity)
            quantityDao.insertRecipeQuantity(RecipeQuantity(recipeWithIngredients.recipe.recipeId!!, quantityId))
        }
    }

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
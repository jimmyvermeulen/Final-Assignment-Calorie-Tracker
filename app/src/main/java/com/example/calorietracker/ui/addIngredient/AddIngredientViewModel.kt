package com.example.calorietracker.ui.addIngredient

import com.example.calorietracker.model.FoodList
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.calorietracker.database.CalorieRepository
import com.example.calorietracker.model.Ingredient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddIngredientViewModel(application: Application) : AndroidViewModel(application) {

    private val calorieRepository = CalorieRepository(application.applicationContext)
    val foodList = MutableLiveData<FoodList>()
    val error = MutableLiveData<String>()

    /**
     * Get a random number trivia from the repository using Retrofit.
     * onResponse if the response is successful populate the [movieList] object.
     * If the call encountered an error then populate the [error] object.
     */
    fun getFoodList(ingredientName : String) {
        calorieRepository.getFoodList(ingredientName).enqueue(object : Callback<FoodList> {
            override fun onResponse(call: Call<FoodList>, response: Response<FoodList>) {
                if (response.isSuccessful) {
                    foodList.value = response.body()
                }
                else {
                    error.value = "An error occurred: ${response.errorBody().toString()}"
                }
            }

            override fun onFailure(call: Call<FoodList>, t: Throwable) {
                error.value = t.message
            }
        })
    }

}
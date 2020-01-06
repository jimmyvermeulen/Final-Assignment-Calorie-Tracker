package com.example.calorietracker.ui.api

import com.example.calorietracker.model.FoodList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

public interface FoodApiService {
    @GET("/api/food-database/parser?nutrition-type=logging&app_id=eb7f6a21&app_key=118803d14a2e22386e6f9547308777f0")
    fun getFoodList(@Query("ingr") ingredient : String): Call<FoodList>
}
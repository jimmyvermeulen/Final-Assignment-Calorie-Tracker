package com.example.calorietracker.ui.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FoodApi {
    companion object {
        // The base url off the api.
        private const val baseUrl = "https://api.edamam.com/"

        /**
         * @return [FoodApiService] The service class off the retrofit client.
         */
        fun createApi(): FoodApiService {
            // Create an OkHttpClient to be able to make a log of the network traffic
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            // Create the Retrofit instance
            val moviesApi = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // Return the Retrofit MoviesApiService
            return moviesApi.create(FoodApiService::class.java)
        }
    }
}
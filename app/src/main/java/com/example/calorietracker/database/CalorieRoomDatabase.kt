package com.example.calorietracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.calorietracker.model.*

@Database(entities = [Day::class, Ingredient::class, Recipe::class, RecipeIngredient::class, DayRecipe::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CalorieRoomDatabase : RoomDatabase() {

    abstract fun dayDao(): DayDao
    abstract fun recipeDao(): RecipeDao
    abstract fun ingredientDao(): IngredientDao
    abstract fun recipeIngredientDao() : RecipeIngredientDao
    abstract fun dayRecipeDao() : DayRecipeDao

    companion object {
        private const val DATABASE_NAME = "CALORIE_DATABASE"

        @Volatile
        private var calorieRoomDatabaseInstance: CalorieRoomDatabase? = null

        fun getDatabase(context: Context): CalorieRoomDatabase? {
            //context.applicationContext.deleteDatabase(DATABASE_NAME)
            if (calorieRoomDatabaseInstance == null) {
                synchronized(CalorieRoomDatabase::class.java) {
                    if (calorieRoomDatabaseInstance == null) {
                        calorieRoomDatabaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            CalorieRoomDatabase::class.java, DATABASE_NAME
                        )
                            .build()
                    }
                }
            }
            return calorieRoomDatabaseInstance
        }
    }

}
package com.example.calorietracker.ui.home

import android.app.Application
import java.util.Calendar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.calorietracker.database.CalorieRepository
import com.example.calorietracker.model.Day
import com.example.calorietracker.model.DayWithRecipes
import com.example.calorietracker.model.RecipeWithIngredients
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val calorieRepository = CalorieRepository(application.applicationContext)

    //val currentTime : Date = GregorianCalendar(Calendar.YEAR, Calendar.DAY_OF_MONTH, Calendar.DAY_OF_MONTH).time

    val recipesWithIngredients: LiveData<List<RecipeWithIngredients>> = calorieRepository.getAllRecipesWithIngredients()

    fun setDay(){
        var calendar : Calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val dayStart : Date = calendar.time
        ioScope.launch {
            calorieRepository.insertDay(Day(dayStart))
        }
    }
}
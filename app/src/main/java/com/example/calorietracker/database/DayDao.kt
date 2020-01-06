package com.example.calorietracker.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.calorietracker.model.Day
import java.util.*

@Dao
interface DayDao {

    @Query("SELECT * FROM dayTable")
    fun getAllDays(): LiveData<List<Day>>

    @Query("SELECT * FROM dayTable WHERE date = :date")
    fun getDay(date: Date): LiveData<Day>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertDay(day: Day)

    @Delete
    suspend fun deleteDay(day: Day)

    @Query("DELETE FROM dayTable")
    suspend fun deleteAllDays()

}
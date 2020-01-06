package com.example.calorietracker.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DayWithRecipes(
    @Embedded val day: Day,
    @Relation(
        parentColumn = "dayId",
        entityColumn = "recipeId",
        associateBy = Junction(DayRecipe::class)
    )
    val recipes: List<Recipe>
) : Parcelable
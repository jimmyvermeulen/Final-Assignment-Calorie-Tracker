package com.example.calorietracker.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecipeWithIngredients(
    @Embedded val recipe: Recipe,
    @Relation(
        parentColumn = "recipeId",
        entityColumn = "ingredientId",
        associateBy = Junction(RecipeIngredient::class)
    )
    val ingredients: List<Ingredient>,
    @Relation(
        parentColumn = "recipeId",
        entityColumn = "quantityId",
        associateBy = Junction(RecipeQuantity::class)
    )
    val quantities: List<Quantity>
) : Parcelable

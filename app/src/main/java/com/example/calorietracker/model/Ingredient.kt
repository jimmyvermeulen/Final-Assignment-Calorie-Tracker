package com.example.calorietracker.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "ingredientTable")
data class Ingredient(

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "calories")
    var calories: Double,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ingredientId")
    var ingredientId: Long? = null

) : Parcelable
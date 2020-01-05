package com.example.calorietracker.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "recipeTable")
data class Recipe(

    @ColumnInfo(name = "name")
    var name: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "recipeId")
    var recipeId: Long? = null

) : Parcelable
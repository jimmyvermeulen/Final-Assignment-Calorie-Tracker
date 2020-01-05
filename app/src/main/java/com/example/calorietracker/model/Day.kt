package com.example.calorietracker.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "dayTable")
data class Day(

    @ColumnInfo(name = "date")
    var date: Date,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "dayId")
    var dayId: Long? = null

) : Parcelable
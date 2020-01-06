package com.example.calorietracker.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "quantityTable")
data class Quantity(

    @ColumnInfo(name = "amount")
    var amount: Double,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "quantityId")
    var quantityId: Long? = null

) : Parcelable
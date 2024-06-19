package com.example.myapplication.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Eating(
    @PrimaryKey(autoGenerate = false)
    val foodCode: String = "",
    val foodName: String,
    var kcal: Double,
    var carbohydrate: Double,
    var protein: Double,
    var fat: Double,
    var gram: Double,
    var personId : String
)

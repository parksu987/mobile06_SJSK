package com.example.myapplication.DB

data class Nutrient(
    var carbohydrate:Double = 0.0,
    var protein:Double = 0.0,
    var fat:Double = 0.0
){
    constructor() : this(0.0, 0.0, 0.0)
}

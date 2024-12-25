package com.example.praktika1.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteCocktail(
    @PrimaryKey val idDrink: String,
    val strDrink: String,
    val strCategory: String?,
    val strInstructions: String?,
    val strDrinkThumb: String?
)
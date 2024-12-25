package com.example.praktika1.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.praktika1.model.FavoriteCocktail

@Dao
interface FavoriteCocktailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteCocktail: FavoriteCocktail)

    @Delete
    suspend fun delete(favoriteCocktail: FavoriteCocktail)

    @Query("SELECT * FROM favorites")
    suspend fun getAllFavorites(): List<FavoriteCocktail>

    @Query("SELECT * FROM favorites WHERE idDrink = :idDrink LIMIT 1")
    suspend fun getFavoriteById(idDrink: String): FavoriteCocktail?
}
package com.example.praktika1.network

import android.content.Context
import com.example.praktika1.db.AppDatabase
import com.example.praktika1.model.Cocktail
import com.example.praktika1.model.FavoriteCocktail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://www.thecocktaildb.com/"

    val apiService: CocktailApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CocktailApiService::class.java)
    }
}

class CocktailRepository(context: Context) {
    private val apiService = RetrofitInstance.apiService
    private val favoriteCocktailDao = AppDatabase.getDatabase(context).favoriteCocktailDao()

    suspend fun getCocktails(): List<Cocktail>? {
        return withContext(Dispatchers.IO) {
            val response = apiService.getAlcoholicCocktails()
            if (response.isSuccessful) {
                response.body()?.drinks
            } else {
                null
            }
        }
    }

    suspend fun getCocktailById(idDrink: String): Cocktail? {
        return withContext(Dispatchers.IO) {
            val response = apiService.getCocktailById(idDrink)
            if (response.isSuccessful) {
                response.body()?.drinks?.firstOrNull()
            } else {
                null
            }
        }
    }

    suspend fun getCocktailsByCategory(category: String): List<Cocktail>? {
        return withContext(Dispatchers.IO) {
            val response = apiService.getCocktailsByCategory(category)
            if (response.isSuccessful) {
                response.body()?.drinks
            } else {
                null
            }
        }
    }

    suspend fun addFavorite(favoriteCocktail: FavoriteCocktail) {
        withContext(Dispatchers.IO) {
            favoriteCocktailDao.insert(favoriteCocktail)
        }
    }

    suspend fun removeFavorite(favoriteCocktail: FavoriteCocktail) {
        withContext(Dispatchers.IO) {
            favoriteCocktailDao.delete(favoriteCocktail)
        }
    }

    suspend fun getAllFavorites(): List<FavoriteCocktail> {
        return withContext(Dispatchers.IO) {
            favoriteCocktailDao.getAllFavorites()
        }
    }

    suspend fun getFavoriteById(idDrink: String): FavoriteCocktail? {
        return withContext(Dispatchers.IO) {
            favoriteCocktailDao.getFavoriteById(idDrink)
        }
    }
}


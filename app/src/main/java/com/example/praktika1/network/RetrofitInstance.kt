package com.example.praktika1.network

import com.example.praktika1.model.Cocktail
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

class CocktailRepository {
    private val apiService = RetrofitInstance.apiService

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
}

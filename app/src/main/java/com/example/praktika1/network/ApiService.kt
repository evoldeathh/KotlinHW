package com.example.praktika1.network

import com.example.praktika1.model.CocktailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApiService {
    @GET("api/json/v1/1/filter.php?a=Alcoholic")
    suspend fun getAlcoholicCocktails(): Response<CocktailResponse>


    @GET("api/json/v1/1/lookup.php")
    suspend fun getCocktailById(@Query("i") idDrink: String): Response<CocktailResponse>
}

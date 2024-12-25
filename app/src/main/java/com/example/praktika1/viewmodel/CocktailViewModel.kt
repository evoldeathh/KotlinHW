package com.example.praktika1.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktika1.model.Cocktail
import com.example.praktika1.network.CocktailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.praktika1.model.FavoriteCocktail

class CocktailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = CocktailRepository(application)

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _cocktailList = MutableStateFlow<List<Cocktail>?>(null)
    val cocktailList: StateFlow<List<Cocktail>?> get() = _cocktailList

    private val _cocktail = MutableStateFlow<Cocktail?>(null)
    val cocktail: StateFlow<Cocktail?> get() = _cocktail

    private val _favorites = MutableStateFlow<List<FavoriteCocktail>>(emptyList())
    val favorites: StateFlow<List<FavoriteCocktail>> get() = _favorites

    init {
        fetchCocktails()
        fetchFavorites()
    }

    private fun fetchCocktails() {
        viewModelScope.launch {
            _isLoading.value = true
            val cocktails = repository.getCocktails()
            _cocktailList.value = cocktails
            _isLoading.value = false
        }
    }

    private fun fetchFavorites() {
        viewModelScope.launch {
            _favorites.value = repository.getAllFavorites()
        }
    }

    fun getCocktailDetail(idDrink: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val cocktailDetails = repository.getCocktailById(idDrink)
            _cocktail.value = cocktailDetails
            _isLoading.value = false
        }
    }

    fun fetchCocktailsByCategory(category: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val cocktails = repository.getCocktailsByCategory(category)
            _cocktailList.value = cocktails
            _isLoading.value = false
        }
    }

    fun addFavorite(cocktail: Cocktail) {
        viewModelScope.launch {
            repository.addFavorite(
                FavoriteCocktail(
                    idDrink = cocktail.idDrink,
                    strDrink = cocktail.strDrink,
                    strCategory = cocktail.strCategory,
                    strInstructions = cocktail.strInstructions,
                    strDrinkThumb = cocktail.strDrinkThumb
                )
            )
            fetchFavorites()
        }
    }

    fun removeFavorite(cocktail: Cocktail) {
        viewModelScope.launch {
            repository.removeFavorite(
                FavoriteCocktail(
                    idDrink = cocktail.idDrink,
                    strDrink = cocktail.strDrink,
                    strCategory = cocktail.strCategory,
                    strInstructions = cocktail.strInstructions,
                    strDrinkThumb = cocktail.strDrinkThumb
                )
            )
            fetchFavorites()
        }
    }
}


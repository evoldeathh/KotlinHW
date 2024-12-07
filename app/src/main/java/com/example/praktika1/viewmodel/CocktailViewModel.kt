package com.example.praktika1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktika1.model.Cocktail
import com.example.praktika1.network.CocktailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log

class CocktailViewModel : ViewModel() {
    private val repository = CocktailRepository()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _cocktailList = MutableStateFlow<List<Cocktail>?>(null)
    val cocktailList: StateFlow<List<Cocktail>?> get() = _cocktailList

    private val _cocktail = MutableStateFlow<Cocktail?>(null)
    val cocktail: StateFlow<Cocktail?> get() = _cocktail

    init {
        fetchCocktails()
    }

    private fun fetchCocktails() {
        viewModelScope.launch {
            _isLoading.value = true
            val cocktails = repository.getCocktails()
            _cocktailList.value = cocktails
            _isLoading.value = false

            // Логирование списка коктейлей
            cocktails?.forEach { cocktail ->
                println("Cocktail name: ${cocktail.strDrink}, Category: ${cocktail.strCategory}")
            }
        }
    }

    fun getCocktailDetail(idDrink: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val cocktailDetails = repository.getCocktailById(idDrink)
            _cocktail.value = cocktailDetails
            _isLoading.value = false

            // Логирование детализированного коктейля
            println("Cocktail Detail: ${cocktailDetails?.strDrink}, Category: ${cocktailDetails?.strCategory}")
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
}


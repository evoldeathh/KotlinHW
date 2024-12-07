@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.praktika1.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.praktika1.R
import com.example.praktika1.model.Cocktail
import com.example.praktika1.viewmodel.CocktailViewModel
import com.example.praktika1.data.store.FilterSettingsDataStore
import com.example.praktika1.data.store.FilterSettings
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailListScreen(
    navController: NavController,
    viewModel: CocktailViewModel,
    dataStore: FilterSettingsDataStore
) {
    val cocktails by viewModel.cocktailList.collectAsState(initial = emptyList()) // Пустой список по умолчанию
    val isLoading by viewModel.isLoading.collectAsState(initial = false)
    val filterSettings by dataStore.filterSettings.collectAsState(initial = FilterSettings("", "", ""))
    var searchQuery by remember { mutableStateOf("") }
    var filteredCocktails by remember { mutableStateOf<List<Cocktail>>(emptyList()) } // Пустой список по умолчанию

    LaunchedEffect(searchQuery, filterSettings, cocktails) {
        // Логируем весь список коктейлей
        //println("Cocktail List: $cocktails")

        filteredCocktails = cocktails?.filter { cocktail ->
            val normalizedCategory = cocktail.strCategory?.trim()?.lowercase()  // Используем безопасный доступ
            val normalizedSearchQuery = searchQuery.trim().lowercase()
            val normalizedFilterCategory = filterSettings.type.trim().lowercase()

            // Логирование для отладки
           // println("Cocktail Category: $normalizedCategory")
           // println("Filter Category: $normalizedFilterCategory")
            println("Filter Settings Type: ${filterSettings.type}")
            println("Cocktail Category: ${cocktail.strCategory}")


            // Применяем фильтрацию
            (searchQuery.isEmpty() || cocktail.strDrink?.contains(normalizedSearchQuery, ignoreCase = true) == true) &&
                    (filterSettings.name.isEmpty() || cocktail.strDrink?.contains(filterSettings.name, ignoreCase = true) == true) &&
                    (filterSettings.type.isEmpty() || normalizedCategory?.contains(normalizedFilterCategory) == true) &&
                    (filterSettings.rating.isEmpty() || cocktail.strAlcoholic?.contains(filterSettings.rating, ignoreCase = true) == true)
        } ?: emptyList()

        // Логируем финальный список после фильтрации
        //println("Filtered Cocktails: $filteredCocktails")
    }




    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cocktail List") },
                actions = {
                    IconButton(onClick = { navController.navigate("filterSettings") }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_filter_list),
                            contentDescription = "Filter"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            TextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                },
                label = { Text("Search Cocktails") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Box(modifier = Modifier.fillMaxSize()) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else {
                    if (filteredCocktails.isNotEmpty()) {
                        LazyColumn {
                            items(filteredCocktails) { cocktail ->
                                CocktailListItem(cocktail, navController)
                            }
                        }
                    } else {
                        Text(
                            text = "No cocktails available",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CocktailListItem(cocktail: Cocktail, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("cocktailDetail/${cocktail.idDrink}")
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = cocktail.strDrinkThumb),
            contentDescription = cocktail.strDrink,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = cocktail.strDrink, style = MaterialTheme.typography.bodyLarge)
    }
}




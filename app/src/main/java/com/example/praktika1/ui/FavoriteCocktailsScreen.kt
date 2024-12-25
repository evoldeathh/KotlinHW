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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.praktika1.model.Cocktail
import com.example.praktika1.model.FavoriteCocktail
import com.example.praktika1.viewmodel.CocktailViewModel

@Composable
fun FavoriteCocktailsScreen(viewModel: CocktailViewModel = viewModel(), navController: NavController) {
    val favoriteCocktails by viewModel.favorites.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (favoriteCocktails.isNotEmpty()) {
            LazyColumn {
                items(favoriteCocktails) { cocktail ->
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



@Composable
fun CocktailListItem(cocktail: FavoriteCocktail, navController: NavController) {
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
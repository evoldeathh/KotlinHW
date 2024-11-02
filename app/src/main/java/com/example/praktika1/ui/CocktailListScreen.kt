@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.praktika1.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.praktika1.model.Cocktail
import com.example.praktika1.viewmodel.CocktailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailListScreen(navController: NavController, viewModel: CocktailViewModel) {
    val cocktails by viewModel.cocktailList.collectAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.collectAsState(initial = false)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cocktail List") }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn {
                    items(cocktails ?: emptyList()) { cocktail ->
                        CocktailListItem(cocktail, navController)
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

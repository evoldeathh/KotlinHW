package com.example.praktika1.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.praktika1.viewmodel.CocktailViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailDetailScreen(
    navController: NavController,
    cocktailId: String?,
    viewModel: CocktailViewModel
) {
    val cocktail by viewModel.cocktail.collectAsState(initial = null)
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(cocktailId) {
        if (cocktailId != null) {
            viewModel.getCocktailDetail(cocktailId)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = cocktail?.strDrink ?: "Cocktail Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                cocktail?.let { drink ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = drink.strDrinkThumb),
                            contentDescription = drink.strDrink,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = drink.strDrink, style = MaterialTheme.typography.headlineMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Category: ${drink.strCategory ?: "Unknown"}", style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Glass: ${drink.strGlass}", style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Instructions: ${drink.strInstructions}", style = MaterialTheme.typography.bodyMedium)
                    }
                } ?: run {
                    Text(text = "Cocktail not found", modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

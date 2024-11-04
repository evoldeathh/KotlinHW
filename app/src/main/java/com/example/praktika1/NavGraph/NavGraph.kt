package com.example.praktika1.NavGraph

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.praktika1.ui.CocktailDetailScreen
import com.example.praktika1.ui.CocktailListScreen
import com.example.praktika1.ui.HomeScreen
import com.example.praktika1.viewmodel.CocktailViewModel

@Composable
fun MyNavHost(navController: NavHostController, paddingValues: PaddingValues, viewModel: CocktailViewModel) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen() }
        composable("cocktailList") { CocktailListScreen(navController, viewModel) }
        composable("cocktailDetail/{cocktailId}") { backStackEntry ->
            CocktailDetailScreen(navController, backStackEntry.arguments?.getString("cocktailId"), viewModel)
        }
    }
}

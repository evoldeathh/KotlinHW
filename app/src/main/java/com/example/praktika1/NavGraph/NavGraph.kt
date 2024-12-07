package com.example.praktika1.NavGraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.praktika1.ui.settings.FilterSettingsScreen
import com.example.praktika1.viewmodel.CocktailViewModel
import com.example.praktika1.data.store.FilterSettingsDataStore
import com.example.praktika1.ui.CocktailDetailScreen
import com.example.praktika1.ui.CocktailListScreen

@Composable
fun NavGraph(navController: NavHostController, viewModel: CocktailViewModel, dataStore: FilterSettingsDataStore) {
    NavHost(navController, startDestination = "cocktailList") {
        composable("cocktailList") {
            CocktailListScreen(navController, viewModel, dataStore)
        }
        composable("filterSettings") {
            FilterSettingsScreen(dataStore, navController)
        }
        composable("cocktailDetail/{cocktailId}") { backStackEntry ->
            val cocktailId = backStackEntry.arguments?.getString("cocktailId")
            cocktailId?.let {
                CocktailDetailScreen(navController, it, viewModel)
            }
        }
    }
}

package com.example.praktika1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.praktika1.NavGraph.NavGraph
import com.example.praktika1.viewmodel.CocktailViewModel
import com.example.praktika1.data.store.FilterSettingsDataStore
import com.example.praktika1.ui.theme.Theme

class MainActivity : ComponentActivity() {
    private val viewModel: CocktailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataStore = FilterSettingsDataStore(applicationContext)

        setContent {
            Theme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavGraph(navController = navController, viewModel = viewModel, dataStore = dataStore)
                }
            }
        }
    }
}

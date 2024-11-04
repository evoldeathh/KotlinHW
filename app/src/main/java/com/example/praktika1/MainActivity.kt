package com.example.praktika1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.praktika1.NavGraph.MyNavHost
import com.example.praktika1.ui.BottomNavigationBar
import com.example.praktika1.viewmodel.CocktailViewModel
import com.example.praktika1.ui.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: CocktailViewModel by viewModels()
        setContent {
            Theme {
                MyApp(viewModel)
            }
        }
    }
}

@Composable
fun MyApp(viewModel: CocktailViewModel) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) } // Добавляем нижнюю навигацию
    ) { paddingValues ->
        MyNavHost(navController = navController, paddingValues = paddingValues, viewModel = viewModel) // Передаем viewModel в NavHost
    }
}

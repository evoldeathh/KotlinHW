@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.praktika1.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.praktika1.data.store.FilterSettingsDataStore
import kotlinx.coroutines.launch

@Composable
fun FilterSettingsScreen(
    dataStore: FilterSettingsDataStore,
    navController: NavController
) {
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var alcohol by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        dataStore.filterSettings.collect { settings ->
            name = settings.name
            category = settings.type
            alcohol = settings.rating
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Filter Settings", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Name") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = category,
            onValueChange = { category = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Category") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = alcohol,
            onValueChange = { alcohol = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Alcohol") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                scope.launch {
                    dataStore.saveFilterSettings(name, category, alcohol)
                    navController.popBackStack()
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Apply Filters")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FilterSettingsScreenPreview() {
    FilterSettingsScreen(
        dataStore = FilterSettingsDataStore(context = LocalContext.current),
        navController = rememberNavController()
    )
}

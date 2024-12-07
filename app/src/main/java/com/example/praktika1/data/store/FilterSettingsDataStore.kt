package com.example.praktika1.data.store

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore by preferencesDataStore("filter_settings")

data class FilterSettings(val name: String, val type: String, val rating: String)

class FilterSettingsDataStore(context: Context) {
    private val dataStore = context.dataStore

    val filterSettings = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val name = preferences[stringPreferencesKey("filter_name")] ?: ""
            val type = preferences[stringPreferencesKey("filter_type")] ?: ""
            val rating = preferences[stringPreferencesKey("filter_rating")] ?: ""
            FilterSettings(name, type, rating)
        }

    suspend fun saveFilterSettings(name: String, type: String, rating: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey("filter_name")] = name
            preferences[stringPreferencesKey("filter_type")] = type
            preferences[stringPreferencesKey("filter_rating")] = rating
        }
    }
}

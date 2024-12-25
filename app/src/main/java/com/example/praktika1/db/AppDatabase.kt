package com.example.praktika1.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.praktika1.model.FavoriteCocktail

@Database(entities = [FavoriteCocktail::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteCocktailDao(): FavoriteCocktailDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cocktail_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

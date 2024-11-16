package com.tytbutler.Pantry.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tytbutler.Pantry.data.dao.ItemDao
import com.tytbutler.Pantry.data.dao.RecipeDao
import com.tytbutler.Pantry.data.entity.Item
import com.tytbutler.Pantry.data.entity.Recipe

@Database(entities = [Item::class, Recipe::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return instance ?: synchronized (this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "pantry-db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {instance = it}
            }
        }
    }
}
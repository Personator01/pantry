package com.tytbutler.Pantry.data.db

import androidx.room.Database
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
}
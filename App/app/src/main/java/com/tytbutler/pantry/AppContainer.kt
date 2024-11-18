package com.tytbutler.pantry

import android.content.Context
import com.tytbutler.Pantry.data.db.AppDatabase
import com.tytbutler.Pantry.data.entity.Recipe
import com.tytbutler.pantry.data.repository.ItemRepository
import com.tytbutler.pantry.data.repository.RecipeRepository

interface AppContainer {
    val itemRepository: ItemRepository
    val recipeRepository: RecipeRepository
}

class AppDataContainer(private val context: Context): AppContainer {
    override val itemRepository: ItemRepository by lazy {
        ItemRepository(AppDatabase.getDatabase(context).itemDao());
    }

    override val recipeRepository: RecipeRepository by lazy {
        RecipeRepository(AppDatabase.getDatabase(context).recipeDao());
    }
}
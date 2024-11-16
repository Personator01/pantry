package com.tytbutler.pantry

import android.content.Context
import com.tytbutler.Pantry.data.db.AppDatabase
import com.tytbutler.pantry.data.repository.ItemRepository

interface AppContainer {
    val itemRepository: ItemRepository
}

class AppDataContainer(private val context: Context): AppContainer {
    override val itemRepository: ItemRepository by lazy {
        ItemRepository(AppDatabase.getDatabase(context).itemDao());
    }
}
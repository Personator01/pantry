package com.tytbutler.pantry.data.repository

import com.tytbutler.Pantry.data.dao.ItemDao
import com.tytbutler.Pantry.data.entity.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.singleOrNull

class ItemRepository(private val itemDao: ItemDao) {
    suspend fun addItem(item: Item) {
        println("Adding item")
        if (item.id.isBlank()) {
            throw IllegalArgumentException("attempted to add an item without an id")
        }
        itemDao.insert(item);
    }

    suspend fun needItem(item: Item) {
        itemDao.update(item.copy(isNeeded = true))
    }

    suspend fun unNeedItem(item: Item) {
        itemDao.update(item.copy(isNeeded = false))
    }

    suspend fun delItem(item: Item) = itemDao.delete(item)

    suspend fun searchItems(searchTerm: String): Flow<List<Item>> {
        return if (searchTerm.isNotBlank()) {
            val search_split = searchTerm.split(',').map { s -> "'*$s*'" }
            itemDao.searchItems(search_split.joinToString(" OR ") )
        } else {
            itemDao.getAll()
        }
    }

    suspend fun needAll(ids: Set<String>) {
        ids.forEach {
            itemDao.needItem(it)
        }
    }

    fun getNeededStream(): Flow<List<Item>> = itemDao.getNeeded()

    fun getAll(): Flow<List<Item>> = itemDao.getAll()

    fun getItemStream(id: String): Flow<Item?> = itemDao.getItemStream(id)

    suspend fun getItem(id: String): Item? = itemDao.getItem(id)

    suspend fun getName(id: String): String? = itemDao.getItemName(id);
}
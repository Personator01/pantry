package com.tytbutler.pantry.data.repository

import com.tytbutler.Pantry.data.dao.ItemDao
import com.tytbutler.Pantry.data.entity.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.single

class ItemRepository(private val itemDao: ItemDao) {
    suspend fun addItem(item: Item) {
        val item_already = itemDao.getItem(item.id);
        if (item_already.single() == null) {
            itemDao.insert(item);
        } else {
            itemDao.update(item);
        }
    }

    suspend fun needItem(item: Item) {
        itemDao.update(item.copy(isNeeded = true))
    }

    suspend fun unNeedItem(item: Item) {
        itemDao.update(item.copy(isNeeded = false))
    }

    fun getNeededStream(): Flow<List<Item>> = itemDao.getNeeded()

}
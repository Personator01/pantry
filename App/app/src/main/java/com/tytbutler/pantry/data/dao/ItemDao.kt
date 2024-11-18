package com.tytbutler.Pantry.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tytbutler.Pantry.data.entity.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Insert
    suspend fun insert(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Update
    suspend fun update(item: Item)

    @Query("select * from Item where isNeeded = true")
    fun getNeeded(): Flow<List<Item>>

    @Query("select * from Item where id = :id limit 1")
    suspend fun getItem(id: String): Item?

    @Query("select * from Item where id = :id limit 1")
    fun getItemStream(id: String): Flow<Item?>

    @Query("select * " +
            "from Item join ItemFts " +
            "on Item.id = ItemFts.id " +
            "where name match :searchTerm")
    fun searchItems(searchTerm: String): Flow<List<Item>>

    @Query("select * from Item order by id")
    fun getAll(): Flow<List<Item>>

    @Query("select name from Item where id = :id limit 1")
    fun getItemName(id: String): String?

    @Query("update Item " +
            "set isNeeded = true " +
            "where id = :id")
    suspend fun needItem(id: String)
}
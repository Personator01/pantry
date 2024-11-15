package com.tytbutler.Pantry.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.tytbutler.Pantry.data.entity.Item

@Dao
interface ItemDao {
    @Insert
    fun insert(item: Item)

    @Delete
    fun delete(item: Item)

    @Update
    fun update(item: Item)
}
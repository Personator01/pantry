package com.tytbutler.Pantry.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tytbutler.Pantry.data.entity.Recipe

@Dao
interface RecipeDao {
    @Insert
    fun insert(recipe: Recipe)

    @Delete
    fun delete(recipe: Recipe)

    @Update
    fun update(recipe: Recipe)

    @Query("SELECT * FROM Recipe WHERE name MATCH :term")
    fun getRecipesBySearch(term: String)
}
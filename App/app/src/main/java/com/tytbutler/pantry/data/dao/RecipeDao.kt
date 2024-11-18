package com.tytbutler.Pantry.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tytbutler.Pantry.data.entity.Item
import com.tytbutler.Pantry.data.entity.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Insert
    suspend fun insert(recipe: Recipe)

    @Delete
    suspend fun delete(recipe: Recipe)

    @Update
    suspend fun update(recipe: Recipe)

    @Query("select Recipe.id, Recipe.name, Recipe.description, Recipe.ingredients " +
            "from Recipe join RecipeFts " +
            "on Recipe.id = RecipeFts.id " +
            "where RecipeFts.name or RecipeFts.ingredients match :term")
    fun searchRecipes(term: String): Flow<List<Recipe>>

    @Query("select * from Recipe order by id")
    fun getAll(): Flow<List<Recipe>>

    @Query("select * from Recipe where id = :id limit 1")
    fun getRecipe(id: String): Flow<Recipe?>

}
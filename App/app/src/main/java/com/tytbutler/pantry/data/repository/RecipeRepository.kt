package com.tytbutler.pantry.data.repository

import com.tytbutler.Pantry.data.dao.RecipeDao
import com.tytbutler.Pantry.data.entity.Item
import com.tytbutler.Pantry.data.entity.Recipe
import kotlinx.coroutines.flow.Flow

class RecipeRepository( private val recipeDao: RecipeDao){
    fun getRecipeStream(id: String): Flow<Recipe?> = recipeDao.getRecipe(id)

    fun searchRecipes(queryTerm: String): Flow<List<Recipe>> {
        return if (queryTerm.isNotBlank()) {
            recipeDao.searchRecipes(queryTerm)
        } else {
            recipeDao.getAll()
        }
    }

    fun add(recipe: Recipe) = recipeDao::insert

    fun update(recipe: Recipe) = recipeDao::update

    suspend fun deleteRecipe(recipe: Recipe) = recipeDao.delete(recipe)
}
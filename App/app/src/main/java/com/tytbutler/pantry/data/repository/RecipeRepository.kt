package com.tytbutler.pantry.data.repository

import com.tytbutler.Pantry.data.dao.RecipeDao
import com.tytbutler.Pantry.data.entity.Item
import com.tytbutler.Pantry.data.entity.Recipe
import kotlinx.coroutines.flow.Flow

class RecipeRepository( private val recipeDao: RecipeDao){
    suspend fun getRecipe(id: String): Recipe? = recipeDao.getRecipe(id)


    suspend fun searchRecipes(queryTerm: String): Flow<List<Recipe>> {
        return if (queryTerm.isNotBlank()) {
            val search_split = queryTerm.split(',').map { s -> "'*$s*'" }
            recipeDao.searchRecipes(search_split.joinToString(" OR ") )
        } else {
            recipeDao.getAll()
        }
    }

    suspend fun add(recipe: Recipe) {
        println("Adding recipe ${recipe.name}")
        recipeDao.insert(recipe);
    }

    suspend fun update(recipe: Recipe) {
        println("Updating recipe ${recipe.name}")
        recipeDao.update(recipe)
    }

    suspend fun deleteRecipe(recipe: Recipe) = recipeDao.delete(recipe)
}
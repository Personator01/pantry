package com.tytbutler.pantry.ui.state

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tytbutler.Pantry.data.dao.RecipeDao
import com.tytbutler.Pantry.data.entity.Recipe
import com.tytbutler.pantry.data.repository.ItemRepository
import com.tytbutler.pantry.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecipeScreenViewModel(private val recipeRepository: RecipeRepository,
    private val itemRepository: ItemRepository) : ViewModel() {

    private var _isEdit = MutableStateFlow(false)
    val isEdit = _isEdit.asStateFlow()

    private var _isCreate = MutableStateFlow(false)
    val isCreate = _isCreate.asStateFlow()

    private var _editingRecipe = MutableStateFlow(Recipe.empty())
    val editingRecipe = _editingRecipe.asStateFlow()

    private var _queryTerm = MutableStateFlow("")
    val queryTerm = _queryTerm.asStateFlow()

    val searchedRecipes = recipeRepository.searchRecipes(_queryTerm.value)

    fun openEdit(recipe: Recipe) {
        _editingRecipe.value = recipe
        _isCreate.value = false
        _isEdit.value = true
    }

    fun openCreate() {
        _isCreate.value = true
        _editingRecipe.value = Recipe.empty()
        _isEdit.value = true
    }

    fun closeEdit() {
        _isEdit.value = false
    }

    fun updateQuery(query: String) {
        _queryTerm.value = query
    }

    fun delRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipeRepository.deleteRecipe(recipe)
        }
    }

    fun addToList(recipe: Recipe) {
        viewModelScope.launch {
            itemRepository.needAll(recipe.ingredients)
        }
    }

}
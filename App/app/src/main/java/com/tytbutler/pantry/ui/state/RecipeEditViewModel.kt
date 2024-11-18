package com.tytbutler.pantry.ui.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tytbutler.Pantry.data.entity.Item
import com.tytbutler.Pantry.data.entity.Recipe
import com.tytbutler.pantry.data.repository.ItemRepository
import com.tytbutler.pantry.data.repository.RecipeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.launch

class RecipeEditViewModel(private val recipeRepository: RecipeRepository,
                          private val itemRepository: ItemRepository): ViewModel() {
    private var _currentRecipe = MutableStateFlow(Recipe.empty())
    val currentRecipe = _currentRecipe.asStateFlow()

    private var _isExistsRecipe = MutableStateFlow(false)
    val isExistsRecipe = _isExistsRecipe.asStateFlow()

    //Pairs of ingredient ids and their corresponding names
    private var _ingredientStrings: MutableStateFlow<List<Pair<String, String>>> = MutableStateFlow(listOf())
    val ingredientStrings = _ingredientStrings.asStateFlow()

    private var _isAddIngredient = MutableStateFlow(false)
    val isAddIngredient = _isAddIngredient.asStateFlow()

    private var _enableSubmit = MutableStateFlow(true)
    val enableSubmit = _enableSubmit.asStateFlow()

    fun loadRecipe(recipe: Recipe) {
        _currentRecipe.value = recipe
        updateRecipeName(_currentRecipe.value.name); // just reloads exists
        updateIngredientStrings()
    }

    fun updateRecipeName(name: String) {
        _enableSubmit.value = false
        _currentRecipe.value = _currentRecipe.value.copy(name = name, id = Recipe.nameToId(name))
        viewModelScope.launch {
            _isExistsRecipe.value = recipeRepository.getRecipe(_currentRecipe.value.id) != null
            _enableSubmit.value = true
        }
    }

    fun addIngredient(id: String) {
        val currentIngredients = _currentRecipe.value.ingredients
        val newIngredients = currentIngredients.plus(id)
        _currentRecipe.value = _currentRecipe.value.copy(ingredients = newIngredients)
        updateIngredientStrings()
    }

    fun removeIngredient(id: String) {
        val currentIngredients = _currentRecipe.value.ingredients
        val newIngredients = currentIngredients.minus(id)
        _currentRecipe.value = _currentRecipe.value.copy(ingredients = newIngredients)
        updateIngredientStrings()
    }

    fun updateDescription(desc: String) {
        _currentRecipe.value = _currentRecipe.value.copy(description = desc)
    }

    fun openAddIngredient() {
        _isAddIngredient.value = true
    }

    fun closeAddIngredient() {
        _isAddIngredient.value = false
    }

    fun addNewRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipeRepository.add(recipe)
        }
    }

    fun updateRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipeRepository.update(recipe)
        }
    }

    private fun updateIngredientStrings() {
        _enableSubmit.value = false
        viewModelScope.launch {
            _ingredientStrings.value = _currentRecipe.value.ingredients.map {
                val name = itemRepository.getName(it)
                Pair(it, name ?: ("Item $it does not exist"))
            }
            _enableSubmit.value = true
        }
    }
}
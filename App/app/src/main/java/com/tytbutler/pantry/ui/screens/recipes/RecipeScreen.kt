package com.tytbutler.pantry.ui.screens.recipes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tytbutler.Pantry.data.entity.Recipe
import com.tytbutler.pantry.ui.AppDialog
import com.tytbutler.pantry.ui.AppViewModelProvider
import com.tytbutler.pantry.ui.screens.Bar
import com.tytbutler.pantry.ui.screens.Screen
import com.tytbutler.pantry.ui.screens.editors.RecipeEditor
import com.tytbutler.pantry.ui.state.RecipeScreenViewModel

@Composable
fun RecipeScreen(
    viewModel: RecipeScreenViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onNavClick: (Screen) -> Unit
) {
    val isEdit by viewModel.isEdit.collectAsState()
    if (!isEdit) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = viewModel::openCreate
                ) {
                    Icon(Icons.Default.Add, "Add Recipe")
                }
            },
            bottomBar = { Bar(onNavClick) },
        ) { padding ->
            val queryTerm by viewModel.queryTerm.collectAsState()
            Column(modifier = Modifier.padding(padding)) {
                TextField(
                    value = queryTerm,
                    onValueChange = viewModel::updateQuery,
                    trailingIcon = {
                        Icon(Icons.Default.Clear, "Clear Search",
                            modifier = Modifier.clickable { viewModel.updateQuery("") })
                    })
                RecipeList(viewModel)
            }
        }
    } else {
        val isCreate by viewModel.isCreate.collectAsState()
        val editingRecipe by viewModel.editingRecipe.collectAsState()
        RecipeEditor(
            createMode = isCreate,
            onExit = viewModel::closeEdit,
            inRecipe = editingRecipe,
        )
    }
}

@Composable
fun RecipeList(viewModel: RecipeScreenViewModel, modifier: Modifier = Modifier) {
    val searchedRecipes by viewModel.searchedRecipes.collectAsState(listOf());
    LazyColumn {
        items(searchedRecipes) {
            RecipeCard(it, viewModel)
        }
    }
}

@Composable
fun RecipeCard(recipe: Recipe, viewModel: RecipeScreenViewModel) {
    var isDelDialogOpen by remember { mutableStateOf(false) }
    Row (horizontalArrangement = Arrangement.SpaceBetween) {
        Text(recipe.name)
        Row {
            Button(
                onClick = {isDelDialogOpen = true}
            ) {
                Icon(Icons.Default.Delete, "Delete recipe")
            }
            Button(
                onClick = {viewModel.openEdit(recipe)}
            ) {
                Icon(Icons.Default.Edit, "Edit recipe")
            }
            Button(
                onClick = {viewModel.addToList(recipe)}
            ) {
                Icon(Icons.Default.Add, "Add ingredients to list")
            }
        }
    }
    if (isDelDialogOpen) {
        AppDialog(
            message = "Are you sure you want to delete ${recipe.name}? This cannot be undone.",
            onDismiss = { isDelDialogOpen = false },
            enableSecondButton = true,
            secondButtonText = "Delete",
            onSecondButton = { viewModel.delRecipe(recipe) }
        )
    }
}
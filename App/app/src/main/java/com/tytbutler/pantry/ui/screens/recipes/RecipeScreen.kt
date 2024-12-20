package com.tytbutler.pantry.ui.screens.recipes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tytbutler.Pantry.data.entity.Recipe
import com.tytbutler.pantry.ui.AppDialog
import com.tytbutler.pantry.ui.AppViewModelProvider
import com.tytbutler.pantry.ui.screens.NavBar
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
                    onClick = viewModel::openCreate,
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Add, "Add Recipe")
                }
            },
            bottomBar = { NavBar(onNavClick) },
        ) { padding ->
            val queryTerm by viewModel.queryTerm.collectAsState()
            LaunchedEffect(queryTerm) { viewModel.searchRecipes() }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(padding).padding(20.dp).fillMaxWidth()) {
                TextField(
                    value = queryTerm,
                    onValueChange = viewModel::updateQuery,
                    trailingIcon = {
                        Icon(Icons.Default.Clear, "Clear Search",
                            modifier = Modifier.clickable { viewModel.updateQuery("") })
                    })
                Spacer(modifier = Modifier.height(20.dp).fillMaxWidth())
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
fun RecipeList(viewModel: RecipeScreenViewModel, modifier: Modifier = Modifier.fillMaxWidth()) {
    val searchedRecipesStream by viewModel.searchedRecipes.collectAsState()
    val searchedRecipes by searchedRecipesStream.collectAsState(listOf())
    LazyColumn {
        items(searchedRecipes) {
            RecipeCard(it, viewModel)
        }
        item {
            Spacer(Modifier.height(100.dp))
        }
    }
}

@Composable
fun RecipeCard(recipe: Recipe, viewModel: RecipeScreenViewModel) {
    var isDelDialogOpen by remember { mutableStateOf(false) }
    Row (horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()) {
        Text(recipe.name)
        Row {
            Button(
                onClick = {isDelDialogOpen = true},
                shape = CircleShape,
            ) {
                Icon(Icons.Default.Delete, "Delete recipe")
            }
            Button(
                onClick = {viewModel.openEdit(recipe)},
                shape = CircleShape,
            ) {
                Icon(Icons.Default.Edit, "Edit recipe")
            }
            Button(
                onClick = {viewModel.addToList(recipe)},
                shape = CircleShape,
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
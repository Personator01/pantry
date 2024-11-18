package com.tytbutler.pantry.ui.screens.editors

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tytbutler.Pantry.data.entity.Recipe
import com.tytbutler.pantry.ui.AppViewModelProvider
import com.tytbutler.pantry.ui.screens.items.ItemSearch
import com.tytbutler.pantry.ui.screens.ReturnBar
import com.tytbutler.pantry.ui.state.RecipeEditViewModel
import com.tytbutler.pantry.util.Either

@Composable
fun RecipeEditor(
    createMode: Boolean,
    inRecipe: Recipe = Recipe.empty(), // ignored if createMode = true
    onExit: () -> Unit,
    viewModel: RecipeEditViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val isAddIngredient by viewModel.isAddIngredient.collectAsState()
    LaunchedEffect(inRecipe) {
        println("loading the recipe!")
        viewModel.loadRecipe(inRecipe);
    }
    Scaffold(topBar = {ReturnBar(onExit)}) { padding ->
        if (!isAddIngredient) {
            val recipe by viewModel.currentRecipe.collectAsState()
            val ingredients by viewModel.ingredientStrings.collectAsState()
            val exists by viewModel.isExistsRecipe.collectAsState()
            val enableSubmit by viewModel.enableSubmit.collectAsState()
            BackHandler(enabled = true, onBack = onExit)
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth().height(1000.dp).padding(padding)) {
                if (createMode) {
                    Text("Recipe Name", textAlign = TextAlign.Center)
                    TextField(
                        value = recipe.name,
                        onValueChange = viewModel::updateRecipeName,
                    );
                } else {
                    Text(recipe.name, textAlign = TextAlign.Center)
                }
                Spacer(Modifier.height(10.dp))
                Text("Recipe Description", textAlign = TextAlign.Center)
                TextField(
                    value = recipe.description,
                    onValueChange = viewModel::updateDescription
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Ingredients")
                    Button(
                        onClick = viewModel::openAddIngredient
                    ) {
                        Icon(Icons.Default.Add, "")
                    }
                }
                RecipeItemList(ingredients, viewModel)
                EditorSubmit (enable = enableSubmit) {
                    if (recipe.name.isBlank()) {
                        Either.Right("Enter a recipe name!")
                    } else {
                        if (createMode) {
                            if (exists) {
                                Either.Right("Recipe ${recipe.name} already exists!")
                            } else {
                                viewModel.addNewRecipe(recipe);
                                onExit()
                                Either.Left(Unit)
                            }
                        } else {
                            if (exists) {
                                viewModel.updateRecipe(recipe);
                                onExit()
                                Either.Left(Unit)
                            } else {
                                Either.Right("Recipe ${recipe.name} " +
                                        "does not exist and can't be edited!")
                            }
                        }
                    }
                }
            }
        } else {
            ItemSearch(
                onItemSelect = {viewModel.addIngredient(it.id); viewModel.closeAddIngredient()},
                buttonIcon = Icons.Default.Add,
                enableSecondaryButton = false,
                onBack = viewModel::closeAddIngredient
            )
        }
    }
}

@Composable
fun RecipeItemList(items: List<Pair<String, String>>, viewModel: RecipeEditViewModel, modifier: Modifier = Modifier) {
    LazyColumn {
        items(items) {
            ItemCard(it, viewModel)
        }
    }
}

@Composable
fun ItemCard(item: Pair<String, String>, viewModel: RecipeEditViewModel, modifier: Modifier = Modifier) {
    Card(modifier = Modifier) {
        Row (horizontalArrangement = Arrangement.SpaceBetween){
            Text(item.second)
            Button(
                onClick = {viewModel.removeIngredient(item.first)}
            ) {
                Icon(Icons.Default.Clear, "")
            }
        }
    }
}


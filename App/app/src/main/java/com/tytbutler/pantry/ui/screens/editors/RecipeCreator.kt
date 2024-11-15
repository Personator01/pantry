package com.tytbutler.pantry.ui.screens.editors

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun RecipeCreator(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    //var ingredients: MutableState<List<String>> by remember { mutableStateOf(emptyList<String>()) }
    Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier){
        TextField(
            value = name,
            onValueChange = {name = it},
        );
        TextField(
            value = description,
            onValueChange = { description = it }
        )
    }
}

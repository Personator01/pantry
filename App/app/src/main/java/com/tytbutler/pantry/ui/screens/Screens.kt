package com.tytbutler.pantry.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.tytbutler.pantry.ui.screens.items.ItemsScreen
import com.tytbutler.pantry.ui.screens.list.ListScreen
import com.tytbutler.pantry.ui.screens.recipes.RecipeScreen

enum class Screen {
    List,
    Items,
    Recipes
}

@Composable
fun Screens() {
    var currentScreen by remember { mutableStateOf(Screen.List) }
    val navCallback = {screen: Screen ->
        println("navigating to $screen")
        currentScreen = screen}
    when (currentScreen) {
        Screen.List -> ListScreen(onNavClick = navCallback)
        Screen.Items -> ItemsScreen(onNavClick = navCallback)
        Screen.Recipes -> RecipeScreen(onNavClick = navCallback)
    }
}
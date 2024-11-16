package com.tytbutler.pantry.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tytbutler.pantry.ui.screens.list.ListScreen
import kotlinx.serialization.Serializable

@Serializable
data object NavList;

@Composable
fun Screens() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavList) {
        composable<NavList> { ListScreen() }
    }
}
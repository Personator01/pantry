package com.tytbutler.pantry.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

@Composable
fun Bar(onNavClick: (Screen) -> Unit) {
    BottomAppBar {
        Row (horizontalArrangement = Arrangement.SpaceBetween) {
            Button(
                onClick = {onNavClick(Screen.List)}
            ) {
                Icon(Icons.Default.ShoppingCart, "List")
            }
            Button(
                onClick = {onNavClick(Screen.Recipes)}
            ) {
                Icon(Icons.Default.Menu, "Recipes")
            }
            Button(
                onClick = {onNavClick(Screen.Items)}
            ) {
                Icon(Icons.AutoMirrored.Default.List, "Items")
            }
        }
    }
}
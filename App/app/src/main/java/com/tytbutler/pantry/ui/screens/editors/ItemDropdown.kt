package com.tytbutler.pantry.ui.screens.editors

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.tytbutler.Pantry.data.entity.Item
import kotlin.math.exp


@Composable
fun ItemDropdown(init: Item.Category,
                 onSelect: (Item.Category) -> Unit,
                 modifier: Modifier = Modifier) {
    val items = Item.Category.entries
    println()
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(items.indexOf(init)) }
    Box(modifier = modifier) {
        Text(items[selected].toString(),
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { expanded = !expanded})
                .background(Color.Gray))
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEachIndexed{ i, x ->
                DropdownMenuItem(
                    text = {Text(text = x.toString())},
                    onClick = {
                    selected = i;
                    expanded = false;
                        onSelect(Item.Category.fromInt(selected + 1));
                })
            }
        }
    }
}

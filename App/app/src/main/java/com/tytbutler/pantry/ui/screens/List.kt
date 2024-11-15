package com.tytbutler.pantry.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tytbutler.Pantry.data.entity.Item

@Composable
fun List(items: List<Item>, onDelItem: (Item) -> Unit, modifier: Modifier = Modifier) {
    val itemsGrouped = items.groupBy { it.category }.entries.toList();
    LazyColumn(modifier = modifier) {
        items(itemsGrouped) {
            GroupList(it.key, it.value, onDelItem)
        }
    }
}

@Composable
private fun GroupList(category: Item.Category, group: List<Item>, onDelItem: (Item) -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(category.toString());
        LazyColumn(modifier = modifier) {
            items(group) {
                ItemCard(it, onDelItem)
            }
        }
    }
}

@Composable
private fun ItemCard(item: Item, onDelItem: (Item) -> Unit, modifier: Modifier = Modifier) {
    Card (modifier = modifier) {
        Row {
            Text(item.name)
            Button(onClick = {onDelItem(item)}) {
                Text("\uD83D\uDDF8");
            }
        }
    }
}
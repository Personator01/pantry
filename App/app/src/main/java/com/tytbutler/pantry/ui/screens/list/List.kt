package com.tytbutler.pantry.ui.screens.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tytbutler.Pantry.data.entity.Item

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun List(items: List<Item>, onDelItem: (Item) -> Unit, modifier: Modifier = Modifier) {
    val itemsGrouped = items.groupBy { it.category }.entries.toList();
    LazyColumn(modifier = modifier.padding(10.dp, 100.dp)) {
        itemsGrouped.map { (k, v) ->
            stickyHeader {
                Text(text = k.toString())
            }
            GroupList(v, onDelItem)
        }
    }
}

private fun LazyListScope.GroupList(group: List<Item>, onDelItem: (Item) -> Unit, modifier: Modifier = Modifier) {
        items(group) {
            ItemCard(it, onDelItem)
        }
}

@Composable
private fun ItemCard(item: Item, onDelItem: (Item) -> Unit, modifier: Modifier = Modifier) {
    Card (modifier = modifier.fillMaxWidth()) {
        Row (modifier = Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween){
            Text(item.name, modifier = Modifier.padding(start = 20.dp))
            Button(onClick = {onDelItem(item)}) {
                Icon(Icons.Filled.Check, "");
            }
        }
    }
}
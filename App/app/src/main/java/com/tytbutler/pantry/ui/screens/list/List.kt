package com.tytbutler.pantry.ui.screens.list

import android.content.res.Resources.Theme
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tytbutler.Pantry.data.entity.Item
import com.tytbutler.pantry.ui.theme.PantryTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun List(items: List<Item>, onDelItem: (Item) -> Unit, modifier: Modifier = Modifier) {
    val itemsGrouped = items.groupBy { it.category }.
    entries.sortedBy { Item.Category.entriesOrdered().indexOf(it.key) }.toList();
    LazyColumn(modifier = modifier.padding(10.dp, 10.dp)) {
        itemsGrouped.map { (k, v) ->
            stickyHeader {
                Row (modifier= Modifier.fillMaxWidth()
                    .height(40.dp)
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = k.toString())
                }
            }
            GroupList(v, onDelItem)
        }
        item {
            Spacer(Modifier.height(100.dp))
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
        Row (modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 2.dp)
            .height(40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween){
            Text(item.name, modifier = Modifier.padding(start = 20.dp)
                .height(20.dp)
                )
            Button(onClick = {onDelItem(item)}) {
                Icon(Icons.Filled.Check, "");
            }
        }
    }
}
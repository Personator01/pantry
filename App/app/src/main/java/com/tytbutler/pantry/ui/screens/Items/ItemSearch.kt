package com.tytbutler.pantry.ui.screens.Items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.tytbutler.Pantry.data.entity.Item

@Composable
fun ItemSearch(
    items: List<Item>,
    queryTerm: String,
    onUpdateQuery: (String) -> Unit,
    onItemSelect: (Item) -> Unit,
    buttonIcon: ImageVector = Icons.Filled.Check,
    enableSecondaryButton: Boolean,
    onSecondButton: (Item) -> Unit = {},
    secondButtonCondition: (Item) -> Boolean = {true},
    secondButtonIcon: ImageVector = Icons.Filled.Delete,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        TextField(value = queryTerm, onValueChange = onUpdateQuery,
            trailingIcon = {
                Icon(Icons.Default.Clear, "Clear Search",
                    modifier = Modifier.clickable { onUpdateQuery("") })
            }
        )
        ItemsList(
            itemsList = items,
            onItemSelect = onItemSelect,
            buttonIcon = buttonIcon,
            enableSecondaryButton = enableSecondaryButton,
            onSecondButton = onSecondButton,
            secondButtonCondition = secondButtonCondition,
            secondButtonIcon = secondButtonIcon
        )
    }
}

@Composable
private fun ItemsList(itemsList: List<Item>,
                      onItemSelect: (Item) -> Unit,
                      buttonIcon: ImageVector = Icons.Filled.Check,
                      enableSecondaryButton: Boolean,
                      onSecondButton: (Item) -> Unit = {},
                      secondButtonCondition: (Item) -> Boolean = {true},
                      secondButtonIcon: ImageVector = Icons.Filled.Delete,
                      modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.padding(10.dp)) {
        items(itemsList) {
            ItemCard(it,
                onItemSelect,
                buttonIcon,
                enableSecondaryButton,
                onSecondButton,
                secondButtonCondition,secondButtonIcon)
        }
    }
}

@Composable
private fun ItemCard(item: Item,
                     onItemSelect: (Item) -> Unit,
                     buttonIcon: ImageVector = Icons.Filled.Check,
                     enableSecondaryButton: Boolean,
                     onSecondButton: (Item) -> Unit = {},
                     secondButtonCondition: (Item) -> Boolean = {true},
                     secondButtonIcon: ImageVector = Icons.Filled.Delete,
                     modifier: Modifier = Modifier) {
    Card (modifier = modifier.fillMaxWidth()) {
        Row (modifier = Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween){
            Text(item.name, modifier = Modifier.padding(start = 20.dp))
            Row() {
                if (enableSecondaryButton && secondButtonCondition(item)) {
                    Button(onClick = {onSecondButton(item)}) {
                        Icon(secondButtonIcon, "")
                    }
                }
                Button(onClick = {onItemSelect(item)}) {
                    Icon(buttonIcon, "");
                }
            }
        }
    }
}


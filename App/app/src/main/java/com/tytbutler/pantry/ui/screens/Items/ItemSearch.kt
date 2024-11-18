package com.tytbutler.pantry.ui.screens.Items

import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tytbutler.Pantry.data.entity.Item
import com.tytbutler.pantry.ui.AppViewModelProvider
import com.tytbutler.pantry.ui.screens.ReturnBar
import com.tytbutler.pantry.ui.state.ItemSearchViewModel
import kotlinx.coroutines.launch

@Composable
fun ItemSearch(
    viewModel: ItemSearchViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onItemSelect: (Item) -> Unit,
    buttonIcon: ImageVector = Icons.Filled.Check,
    enableSecondaryButton: Boolean,
    onBack: () -> Unit,
    onSecondButton: (Item) -> Unit = {},
    secondButtonCondition: (Item) -> Boolean = {true},
    secondButtonIcon: ImageVector = Icons.Filled.Delete,
    disableTopBar: Boolean = false,
    modifier: Modifier = Modifier
) {
    val items by viewModel.searchedItems.collectAsState()
    val current_items by items.collectAsState(listOf())
    val query by viewModel.searchQuery.collectAsState()
    BackHandler { onBack() }
    LaunchedEffect(query) { viewModel.searchItems() }
    Scaffold(modifier = Modifier.fillMaxWidth(),
        topBar = { if (!disableTopBar) {ReturnBar(onBack)} }
    ) { padding ->
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.padding(padding).padding(20.dp)) {
            TextField(value = query,
                onValueChange = {viewModel.updateQuery(it)},
                trailingIcon = {
                    Icon(Icons.Default.Clear, "Clear Search",
                        modifier = Modifier.clickable { viewModel.updateQuery("") })
                },
                modifier = Modifier.fillMaxWidth()
            )
            ItemsList(
                itemsList = current_items,
                onItemSelect = onItemSelect,
                buttonIcon = buttonIcon,
                enableSecondaryButton = enableSecondaryButton,
                onSecondButton = onSecondButton,
                secondButtonCondition = secondButtonCondition,
                secondButtonIcon = secondButtonIcon
            )
        }
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


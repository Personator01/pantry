package com.tytbutler.pantry.ui.screens.editors

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.unit.dp
import com.tytbutler.Pantry.data.entity.Item
import com.tytbutler.pantry.ui.state.ItemsViewModel
import com.tytbutler.pantry.util.Either

@Composable
fun ItemCreator(viewModel: ItemsViewModel, createAsNeeded: Boolean, onSubmit: () -> Unit, onBack: () -> Unit, modifier: Modifier = Modifier) {
    val itemState by viewModel.openItem.collectAsState()
    BackHandler(enabled = true, onBack = onBack)
    Column (horizontalAlignment = Alignment.Start,
        modifier = modifier.fillMaxWidth().padding(10.dp, 100.dp)) {
        TextField(
            value = itemState.name,
            onValueChange = { viewModel.updateCurrentName(it) }
        )
        ItemDropdown(itemState.category, {viewModel.updateCurrentCategory(it)})
        EditorSubmit(
            onSubmit = {
                if (itemState.name.isNotBlank()) { // validate
                    Either.Left<Unit, String>(left = Unit).also { onSubmit() }
                } else {
                    Either.Right<Unit, String>("Enter an item name!");
                }
            }
        )
    }
}

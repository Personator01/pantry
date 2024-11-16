package com.tytbutler.pantry.ui.screens.editors

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import com.tytbutler.Pantry.data.entity.Item
import com.tytbutler.pantry.ui.state.ItemsViewModel
import com.tytbutler.pantry.util.Either

@Composable
fun ItemCreator(viewModel: ItemsViewModel, createAsNeeded: Boolean, onSubmit: (Item) -> Unit, onBack: () -> Unit) {
    val itemState by viewModel.openItem.collectAsState()
    BackHandler(enabled = true, onBack = onBack)
    Column (horizontalAlignment = Alignment.Start) {
        TextField(
            value = itemState!!.name,
            onValueChange = { viewModel.updateCurrentName(it) }
        )
        ItemDropdown(itemState!!.category, {viewModel.updateCurrentCategory(it)})
        EditorSubmit(
            onSubmit = {
                if (true) { // validate
                    Either.Left<Unit, String>(left = Unit)
                }
                Either.Right<Unit, String>("Failed to validate item");
            }
        )
    }
}

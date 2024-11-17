package com.tytbutler.pantry.ui.screens.editors

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tytbutler.Pantry.data.entity.Item
import com.tytbutler.pantry.ui.AppViewModelProvider
import com.tytbutler.pantry.ui.state.ItemEditViewModel
import com.tytbutler.pantry.ui.state.ItemsViewModel
import com.tytbutler.pantry.util.Either
import kotlinx.coroutines.launch

@Composable
fun ItemCreator( createAsNeeded: Boolean,
                 onSubmit: () -> Unit,
                 onBack: () -> Unit,
                 viewModel: ItemEditViewModel = viewModel(factory = AppViewModelProvider.Factory),
                 modifier: Modifier = Modifier) {
    val name by viewModel.name.collectAsState()
    val category by viewModel.category.collectAsState()
    val exists by viewModel.isExistsItem.collectAsState(true)
    val coroutineScope = rememberCoroutineScope()
    BackHandler(enabled = true, onBack = onBack)
    Column (horizontalAlignment = Alignment.Start,
        modifier = modifier.fillMaxWidth().padding(10.dp, 100.dp)) {
        TextField(
            value = name,
            onValueChange = { viewModel.updateName(it) }
        )
        ItemDropdown(category, { viewModel.updateCategory(it) })
        EditorSubmit(
            onSubmit = {
                if (name.isBlank()) { // validate
                    Either.Right("Enter an item name!")
                } else if (exists) {
                    Either.Right("Item " + name + " already exists!")
                } else {
                    coroutineScope.launch {
                        viewModel.saveItem(createAsNeeded)
                    }
                    onSubmit();
                    Either.Left(Unit)
                }
            }
        )
    }
}

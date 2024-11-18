package com.tytbutler.pantry.ui.screens.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import com.tytbutler.Pantry.data.entity.Item
import com.tytbutler.pantry.ui.AppViewModelProvider
import com.tytbutler.pantry.ui.screens.Bar
import com.tytbutler.pantry.ui.screens.Screen
import com.tytbutler.pantry.ui.screens.editors.ItemEditor
import com.tytbutler.pantry.ui.state.ItemsViewModel
import kotlinx.coroutines.launch

@Composable
fun ListScreen(viewModel: ItemsViewModel = viewModel(factory = AppViewModelProvider.Factory),
               onNavClick: (Screen) -> Unit,
               modifier: Modifier = Modifier) {
    val isEdit by viewModel.isEdit.collectAsState()
    val items by viewModel.list.collectAsState(listOf());
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        floatingActionButton = {
            if (!isEdit) {
                FloatingActionButton(onClick = { viewModel.openEdit(Item.empty(true)) }) {
                    Icon(Icons.Filled.Add, "")
                }
            }
        },
        bottomBar = { Bar(onNavClick) },
    content = { padding ->
        if (!isEdit) {
            List(
                items = items,
                onDelItem = {
                    val i = it
                    coroutineScope.launch {
                        viewModel.delCurrentItem(i)
                    }
                },
                modifier = Modifier.padding(padding)
            )
        } else {
            ItemEditor(
                createAsNeeded = true,
                onSubmit = viewModel::closeEdit,
                onBack = viewModel::closeEdit,
                modifier = Modifier.padding(padding)
            )
        }
    }
    )
}
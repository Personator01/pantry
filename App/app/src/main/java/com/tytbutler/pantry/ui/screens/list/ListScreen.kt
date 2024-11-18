package com.tytbutler.pantry.ui.screens.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tytbutler.pantry.ui.AppViewModelProvider
import com.tytbutler.pantry.ui.screens.NavBar
import com.tytbutler.pantry.ui.screens.Items.ItemSearch
import com.tytbutler.pantry.ui.screens.Screen
import com.tytbutler.pantry.ui.screens.editors.ItemEditor
import com.tytbutler.pantry.ui.state.ItemsViewModel
import kotlinx.coroutines.launch

@Composable
fun ListScreen(viewModel: ItemsViewModel = viewModel(factory = AppViewModelProvider.Factory),
               onNavClick: (Screen) -> Unit,
               modifier: Modifier = Modifier) {
    val isEdit by viewModel.isEdit.collectAsState()
    val isSearch by viewModel.isSearch.collectAsState()
    val items by viewModel.list.collectAsState(listOf());
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        floatingActionButton = {
            if (!isEdit) {
                Column {
                    FloatingActionButton(shape = CircleShape,
                        onClick = { viewModel.openEdit() }) {
                        Icon(Icons.Default.Add, "Add an item")
                    }
                    Spacer(Modifier.height(20.dp))
                    FloatingActionButton(shape = CircleShape, onClick = { viewModel.openSearch() }) {
                        Icon(Icons.Default.Search, "Search items")
                    }
                }
            }
        },
        bottomBar = { NavBar(onNavClick) },
    content = { padding ->
        if (isEdit) {
            ItemEditor(
                createAsNeeded = true,
                onSubmit = viewModel::closeEdit,
                onBack = viewModel::closeEdit,
                modifier = Modifier.padding(padding)
            )
        } else if (isSearch) {
            ItemSearch(
                onBack = viewModel::closeSearch,
                onItemSelect = { viewModel.needItem(it); viewModel.closeSearch()},
                buttonIcon = Icons.Default.Add,
                enableSecondaryButton = false
            )
        } else {
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
        }
    }
    )
}
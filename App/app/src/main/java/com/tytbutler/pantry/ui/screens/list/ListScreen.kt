package com.tytbutler.pantry.ui.screens.list

import android.annotation.SuppressLint
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
import com.tytbutler.pantry.ui.screens.editors.ItemCreator
import com.tytbutler.pantry.ui.state.ItemsViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListScreen(viewModel: ItemsViewModel = viewModel(factory = AppViewModelProvider.Factory), modifier: Modifier = Modifier) {
    val openItem by viewModel.openItem.collectAsState();
    val items by viewModel.list.collectAsState(listOf());
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {viewModel.openEdit(Item.empty(true))}) {
                Icon(Icons.Filled.Add, "")
            }
        }
    ) {
        if (openItem == null) {
            List(
                items = items,
                onDelItem = {
                    val i = it
                    coroutineScope.launch {
                        viewModel.delCurrentItem()
                    }
                })
        } else {
            ItemCreator(
                viewModel = viewModel,
                createAsNeeded = true,
                onSubmit = {
                    val i = it
                    coroutineScope.launch {
                        viewModel.saveCurrentItem()
                    }
                    viewModel.closeEdit()
                },
                onBack = viewModel::closeEdit
            )
        }
    }
}
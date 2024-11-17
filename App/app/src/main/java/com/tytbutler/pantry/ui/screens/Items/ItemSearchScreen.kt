package com.tytbutler.pantry.ui.screens.Items

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tytbutler.Pantry.data.entity.Item
import com.tytbutler.pantry.ui.AppDialog
import com.tytbutler.pantry.ui.AppViewModelProvider
import com.tytbutler.pantry.ui.screens.editors.ItemCreator
import com.tytbutler.pantry.ui.state.ItemSearchScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun ItemSearchScreen(
    viewModel: ItemSearchScreenViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val queryTerm by viewModel.query.collectAsState()
    val items by viewModel.list.collectAsState(listOf())
    val isAlert by viewModel.isAlert.collectAsState()
    val isEdit by viewModel.isEdit.collectAsState()
    Scaffold(
        floatingActionButton = {
            if (!isEdit) {
                FloatingActionButton(onClick = viewModel::openEdit)
                {
                    Icon(Icons.Filled.Add, "")
                }
            }
        },
        modifier = Modifier
    ) { padding ->
        if (!isEdit) {
            ItemSearch(
                items = items,
                queryTerm = queryTerm,
                onUpdateQuery = viewModel::updateQuery,
                onItemSelect = {
                    viewModel.prepareToDelete(it)
                    viewModel.openAlert(
                        "Are you sure you want to delete " + it.name +
                                "? It may cause errors if a recipe references it."
                    )
                },
                buttonIcon = Icons.Filled.Delete,
                enableSecondaryButton = true,
                secondButtonCondition = {
                    !it.isNeeded
                },
                secondButtonIcon = Icons.Filled.Add,
                onSecondButton = {
                    viewModel.unNeedItem(it)
                },
                modifier = Modifier.padding(padding)
            )
        } else {
            ItemCreator(
                createAsNeeded = false,
                onSubmit = viewModel::closeEdit,
                onBack = viewModel::closeEdit,
                modifier = Modifier.padding(padding)
            )
        }
        if (isAlert) {
            val alertMessage by viewModel.alertMessage.collectAsState()
            AppDialog(
                message = alertMessage,
                onDismiss = viewModel::closeAlert,
                dismissText = "Don't delete",
                enableSecondButton = true,
                secondButtonText = "Delete",
                onSecondButton = viewModel::delCurrent,
                modifier = Modifier.padding(padding)
            )
        }
    }
}



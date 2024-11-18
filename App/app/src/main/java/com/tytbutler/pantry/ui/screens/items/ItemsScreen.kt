package com.tytbutler.pantry.ui.screens.items

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tytbutler.pantry.ui.AppDialog
import com.tytbutler.pantry.ui.AppViewModelProvider
import com.tytbutler.pantry.ui.screens.NavBar
import com.tytbutler.pantry.ui.screens.Screen
import com.tytbutler.pantry.ui.screens.editors.ItemEditor
import com.tytbutler.pantry.ui.state.ItemSearchScreenViewModel

@Composable
fun ItemsScreen(
    viewModel: ItemSearchScreenViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onNavClick: (Screen) -> Unit
) {
    val isAlert by viewModel.isAlert.collectAsState()
    val isEdit by viewModel.isEdit.collectAsState()
    Scaffold(
        floatingActionButton = {
            if (!isEdit) {
                FloatingActionButton(onClick = viewModel::openEdit, shape = CircleShape)
                {
                    Icon(Icons.Filled.Add, "")
                }
            }
        },
        bottomBar = { NavBar(onNavClick) },
        modifier = Modifier
    ) { padding ->
        if (!isEdit) {
            ItemSearch(
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
                    viewModel.needItem(it)
                },
                disableTopBar = true,
                onBack = {},
                modifier = Modifier.padding(padding)
            )
        } else {
            ItemEditor(
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
            )
        }
    }
}



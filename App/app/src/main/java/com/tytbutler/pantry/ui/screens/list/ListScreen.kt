package com.tytbutler.pantry.ui.screens.list

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import com.tytbutler.Pantry.data.dao.ItemDao
import com.tytbutler.pantry.ui.screens.editors.ItemCreator
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListScreen(viewModel: ListViewModel = viewModel(), modifier: Modifier = Modifier) {
    val isEdit by viewModel.isEdit.collectAsState();
    val items by viewModel.list.collectAsState(listOf());
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {viewModel.openEdit()}) {
                Icon(Icons.Filled.Add, "")
            }
        }
    ) {
        if (isEdit) {
            List(
                items = items,
                onDelItem = {
                    val i = it
                    coroutineScope.launch {
                        viewModel.delItem(i)
                    }
                })
        } else {
            ItemCreator(
                createAsNeeded = true,
                onSubmit = {
                    val i = it
                    coroutineScope.launch {
                        viewModel.addItem(it)
                    }
                    viewModel.closeEdit()
                },
                onBack = viewModel::closeEdit
            )
        }
    }
}
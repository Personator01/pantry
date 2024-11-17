package com.tytbutler.pantry.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tytbutler.pantry.PantryApplication
import com.tytbutler.pantry.ui.state.ItemEditViewModel
import com.tytbutler.pantry.ui.state.ItemSearchScreenViewModel
import com.tytbutler.pantry.ui.state.ItemsViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
       initializer {
           ItemsViewModel(pantryApplication().container.itemRepository)
       }
        initializer {
            ItemEditViewModel(pantryApplication().container.itemRepository)
        }
        initializer {
            ItemSearchScreenViewModel(pantryApplication().container.itemRepository)
        }
    }
}

fun CreationExtras.pantryApplication(): PantryApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PantryApplication)
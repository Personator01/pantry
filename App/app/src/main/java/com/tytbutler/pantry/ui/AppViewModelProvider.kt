package com.tytbutler.pantry.ui

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tytbutler.pantry.ui.screens.list.ListViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
       initializer {
           ListViewModel(application.container.repository)
       }
    }

}
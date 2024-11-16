package com.tytbutler.pantry.ui.state

import com.tytbutler.Pantry.data.entity.Item

data class ItemUiState (val name: String, val category: Item.Category, val isNeeded: Boolean)
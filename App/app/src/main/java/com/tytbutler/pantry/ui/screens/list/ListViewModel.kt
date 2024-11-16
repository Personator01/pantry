package com.tytbutler.pantry.ui.screens.list

import androidx.lifecycle.ViewModel
import com.tytbutler.Pantry.data.db.AppDatabase
import com.tytbutler.Pantry.data.entity.Item
import com.tytbutler.pantry.data.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow

class ListViewModel(private val itemRepository: ItemRepository) : ViewModel() {
    private val _isEdit = MutableStateFlow(false)
    val isEdit: StateFlow<Boolean> = _isEdit.asStateFlow()
    val list = itemRepository.getNeededStream();


    fun openEdit() {
        _isEdit.value = true
    }
    fun closeEdit() {
        _isEdit.value = false
    }

    suspend fun delItem(item: Item) {
        itemRepository.unNeedItem(item);
    }

    suspend fun addItem(item: Item) {
        itemRepository.addItem(item)
    }
}
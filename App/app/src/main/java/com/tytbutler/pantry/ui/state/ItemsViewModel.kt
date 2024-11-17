package com.tytbutler.pantry.ui.state

import androidx.lifecycle.ViewModel
import com.tytbutler.Pantry.data.entity.Item
import com.tytbutler.pantry.data.repository.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ItemsViewModel(private val itemRepository: ItemRepository) : ViewModel() {
    val list = itemRepository.getNeededStream();

    private val _isEdit = MutableStateFlow(false);
    val isEdit = _isEdit.asStateFlow();


    /*
    Opens an item for editing.
     */
    fun openEdit(item: Item) {
        println("Open Edit")
        _isEdit.value = true;
    }
    fun closeEdit() {
        println("Close Edit")
        _isEdit.value = false;
    }


    suspend fun delCurrentItem(i: Item) {
        itemRepository.unNeedItem(i);
    }



}
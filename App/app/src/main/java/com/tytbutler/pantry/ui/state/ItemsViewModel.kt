package com.tytbutler.pantry.ui.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tytbutler.Pantry.data.entity.Item
import com.tytbutler.pantry.data.repository.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ItemsViewModel(private val itemRepository: ItemRepository) : ViewModel() {
    val list = itemRepository.getNeededStream();

    private val _isEdit = MutableStateFlow(false);
    val isEdit = _isEdit.asStateFlow();

    private val _isSearch = MutableStateFlow(false)
    val isSearch = _isSearch.asStateFlow()


    /*
    Opens an item for editing.
     */
    fun openEdit() {
        println("Open Edit")
        _isEdit.value = true;
    }
    fun closeEdit() {
        println("Close Edit")
        _isEdit.value = false;
    }

    fun openSearch() {
        _isSearch.value = true
    }
    fun closeSearch() {
        _isSearch.value = false
    }


    suspend fun delCurrentItem(i: Item) {
        itemRepository.unNeedItem(i);
    }

    fun needItem(i: Item) {
        viewModelScope.launch {
            itemRepository.needItem(i)
        }
    }



}
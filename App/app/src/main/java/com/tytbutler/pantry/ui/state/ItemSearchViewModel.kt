package com.tytbutler.pantry.ui.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tytbutler.Pantry.data.entity.Item
import com.tytbutler.pantry.data.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

class ItemSearchViewModel(private val itemRepository: ItemRepository) : ViewModel() {
    private var _searchedItems : MutableStateFlow<Flow<List<Item>>> = MutableStateFlow(emptyFlow())
    val searchedItems = _searchedItems.asStateFlow()

    private var _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    fun updateQuery(query: String) {
        _searchQuery.value = query
    }

    suspend fun searchItems() {
        _searchedItems.value = itemRepository.searchItems(_searchQuery.value)
    }

    fun searchItemsAsync() {
        viewModelScope.launch {
            searchItems()
        }
    }
}
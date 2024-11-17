package com.tytbutler.pantry.ui.state

import androidx.lifecycle.ViewModel
import com.tytbutler.pantry.data.repository.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ItemSearchViewModel(private val itemRepository: ItemRepository) : ViewModel() {
    private var _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    val list = itemRepository.searchItems(_query.value)

    fun updateQuery(query: String) {
        _query.value = query
    }
}
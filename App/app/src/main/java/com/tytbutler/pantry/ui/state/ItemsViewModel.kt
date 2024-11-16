package com.tytbutler.pantry.ui.state

import androidx.lifecycle.ViewModel
import com.tytbutler.Pantry.data.entity.Item
import com.tytbutler.pantry.data.repository.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ItemsViewModel(private val itemRepository: ItemRepository) : ViewModel() {
    val list = itemRepository.getNeededStream();

    private var backingItem: Item? = null
    private val _openItem: MutableStateFlow<ItemUiState?> = MutableStateFlow(null);
    val openItem = _openItem.asStateFlow()


    /*
    Opens an item for editing.
     */
    fun openEdit(item: Item) {
        backingItem = item
        _openItem.value = itemToState(item)
    }
    fun closeEdit() {
        backingItem = null
        _openItem.value = null
    }

    fun updateCurrentName(name: String){
        _openItem.value =_openItem.value!!.copy(name = name)
    }
    fun updateCurrentCategory(category: Item.Category){
        _openItem.value =_openItem.value!!.copy(category = category)
    }
    fun updateCurrentIsNeeded(isNeeded: Boolean) {
        _openItem.value = _openItem.value!!.copy(isNeeded = isNeeded)
    }

    suspend fun delCurrentItem() {
        itemRepository.unNeedItem(backingItem!!);
    }

    suspend fun saveCurrentItem() {
        val toSaveItem = backingItem!!.copy(id = Item.nameToId(backingItem!!.name))
        itemRepository.addItem(toSaveItem);
    }


    private fun itemToState(item: Item): ItemUiState =
        ItemUiState(name = item.name, category = item.category, isNeeded = item.isNeeded)

    private fun applyStateChanges(item: Item, state: ItemUiState): Item =
        item.copy(name = state.name, category = state.category, isNeeded = state.isNeeded)
}
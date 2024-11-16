package com.tytbutler.pantry.ui.state

import androidx.lifecycle.ViewModel
import com.tytbutler.Pantry.data.entity.Item
import com.tytbutler.pantry.data.repository.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ItemsViewModel(private val itemRepository: ItemRepository) : ViewModel() {
    val list = itemRepository.getNeededStream();

    private var backingItem: Item = Item.empty(true)
    private val _openItem: MutableStateFlow<ItemUiState> = MutableStateFlow(itemToState(backingItem));
    val openItem = _openItem.asStateFlow()

    private val _isEdit = MutableStateFlow(false);
    val isEdit = _isEdit.asStateFlow();


    /*
    Opens an item for editing.
     */
    fun openEdit(item: Item) {
        println("Open Edit")
        backingItem = item
        _openItem.value = itemToState(item)
        _isEdit.value = true;
    }
    fun closeEdit() {
        println("Close Edit")
        _isEdit.value = false;
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

    suspend fun delCurrentItem(i: Item) {
        itemRepository.unNeedItem(i);
    }

    suspend fun saveCurrentItem(alterId: Boolean) {
        val toSaveItem = applyStateChanges(backingItem, _openItem.value, alterId)
        println("Save Item " + toSaveItem.name + " " + toSaveItem.id + " " + toSaveItem.category.toString())
        itemRepository.addItem(toSaveItem);
    }


    private fun itemToState(item: Item): ItemUiState =
        ItemUiState(name = item.name, category = item.category, isNeeded = item.isNeeded)

    private fun applyStateChanges(item: Item, state: ItemUiState, alterId: Boolean): Item =
        item.copy(
            id = if (alterId) { Item.nameToId(_openItem.value.name)} else { backingItem.id },
            name = state.name,
            category = state.category,
            isNeeded = state.isNeeded)
}
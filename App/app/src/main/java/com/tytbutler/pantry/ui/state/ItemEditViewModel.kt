package com.tytbutler.pantry.ui.state

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.tytbutler.Pantry.data.entity.Item
import com.tytbutler.pantry.data.repository.ItemRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.transformLatest

class ItemEditViewModel(private val itemRepository: ItemRepository) : ViewModel() {
    private var _id: MutableStateFlow<String> = MutableStateFlow("#none-item")
    private val existingItem = itemRepository.getItemStream(_id.value)
    @OptIn(ExperimentalCoroutinesApi::class)
    val isExistsItem = existingItem.transformLatest<Item?, Boolean> { it != null }


    private var _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private var _category = MutableStateFlow(Item.Category.Misc)
    val category = _category.asStateFlow()

    fun updateName(name: String) {
        _id.value = Item.nameToId(name);
        _name.value = name
    }

    fun updateCategory(category: Item.Category) {
        _category.value = category
    }

    suspend fun saveItem(isNeeded: Boolean) {
        val i = Item(id = _id.value, name = _name.value, category = _category.value, isNeeded = isNeeded)
        println("Save Item " + i.name + " " + i.id + " " + i.category.toString())
        itemRepository.addItem(i);
    }
}
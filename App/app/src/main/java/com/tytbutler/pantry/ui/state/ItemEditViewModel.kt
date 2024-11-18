package com.tytbutler.pantry.ui.state

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tytbutler.Pantry.data.entity.Item
import com.tytbutler.pantry.data.repository.ItemRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.launch

class ItemEditViewModel(private val itemRepository: ItemRepository) : ViewModel() {
    private var _id: MutableStateFlow<String> = MutableStateFlow("#none-item")

    private var _isExistsItem = MutableStateFlow(false)
    val isExistsItem = _isExistsItem.asStateFlow()


    private var _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private var _category = MutableStateFlow(Item.Category.Misc)
    val category = _category.asStateFlow()

    private var _enableSubmit = MutableStateFlow(true)
    val enableSubmit = _enableSubmit.asStateFlow()

    fun updateName(name: String) {
        _enableSubmit.value = false
        _id.value = Item.nameToId(name);
        _name.value = name
        viewModelScope.launch {
            _isExistsItem.value = itemRepository.getItem(_id.value) != null
            _enableSubmit.value = true
        }
    }


    fun updateCategory(category: Item.Category) {
        _category.value = category
    }

    fun saveItem(isNeeded: Boolean) {
        val i = Item(id = _id.value, name = _name.value, category = _category.value, isNeeded = isNeeded)
        println("Save Item " + i.name + " " + i.id + " " + i.category.toString())
        viewModelScope.launch {
            itemRepository.addItem(i);
        }
        updateName("")
        updateCategory(Item.Category.Misc)
    }
}
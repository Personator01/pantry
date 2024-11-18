package com.tytbutler.pantry.ui.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tytbutler.Pantry.data.entity.Item
import com.tytbutler.pantry.data.repository.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ItemSearchScreenViewModel(private val itemRepository: ItemRepository) : ViewModel() {
    private var _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private var _isAlert = MutableStateFlow(false)
    val isAlert = _isAlert.asStateFlow()

    private var _alertMessage = MutableStateFlow("")
    val alertMessage = _alertMessage.asStateFlow()

    private var _toDel = MutableStateFlow(Item.empty(false))
    val toDel = _toDel.asStateFlow()

    private var _isEdit = MutableStateFlow(false)
    val isEdit = _isEdit.asStateFlow()


    fun openEdit() {
        _isEdit.value = true
    }

    fun closeEdit() {
        _isEdit.value = false
    }
    fun openAlert(message: String) {
        _isAlert.value = true
        _alertMessage.value = message
    }

    fun closeAlert() {
        _isAlert.value = false
    }

    fun prepareToDelete(item: Item) {
        _toDel.value = item
    }

    fun delCurrent() {
        viewModelScope.launch {
            itemRepository.delItem(_toDel.value)
        }
    }

    fun needItem(item: Item) {
        viewModelScope.launch {
            itemRepository.needItem(item)
        }
    }
}
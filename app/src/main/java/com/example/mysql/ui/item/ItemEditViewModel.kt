package com.example.mysql.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysql.data.ItemRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ItemEditViewModel (
    savedStateHandle: SavedStateHandle,
    private val itemsRepository: ItemRepository
) : ViewModel() {

    var itemUiState by mutableStateOf(ItemUiState())
        private set

    private val itemId: Int = checkNotNull(savedStateHandle[ItemDetailsDestination.itemIdArg])

    private fun validateInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean {
        return with(uiState) {
            nome.isNotBlank() && preco.isNotBlank() && quantidade > 0
        }
    }

    init {
        viewModelScope.launch {
            itemUiState = itemsRepository.getItemStream(itemId)
                .filterNotNull()
                .first()
                .toItemUiState(true)
        }
    }

    fun updateUiState(itemDetails: ItemDetails){
        itemUiState =
            ItemUiState(itemDetails = itemDetails, isEntryValid = validateInput(itemDetails))
    }

    suspend fun updateItem(){
        if(validateInput(itemUiState.itemDetails)){
            itemsRepository.updateItem(itemUiState.itemDetails.toItem())
        }
    }
}

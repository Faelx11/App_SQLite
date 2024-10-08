package com.example.mysql.ui.item

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysql.data.Item
import com.example.mysql.data.ItemRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ItemDetailsViewModel (
    savedStateHandle: SavedStateHandle,
    private val itemsRepository: ItemRepository
) : ViewModel(){


    private val itemId: Int = checkNotNull(savedStateHandle[ItemDetailsDestination.itemIdArg])

    companion object{
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val uiState: StateFlow<ItemDetailsUiState> =
        itemsRepository.getItemStream(itemId)
            .filterNotNull()
            .map{
                ItemDetailsUiState(itemDetails = it.toItemDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ItemDetailsUiState()
            )

    suspend fun deleteItem(){
        itemsRepository.deleteItem(uiState.value.itemDetails.toItem())
    }
}

data class ItemDetailsUiState(
    val itemDetails: ItemDetails = ItemDetails(),
)
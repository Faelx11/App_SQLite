package com.example.mysql.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mysql.data.Item
import com.example.mysql.data.ItemRepository

class ItemEntryViewModel(private val itemsRepository: ItemRepository) : ViewModel() {

    var itemUiState by mutableStateOf(ItemUiState())
        private set

    fun updateUiState(itemDetails: ItemDetails){
        itemUiState =
            ItemUiState(itemDetails = itemDetails, isEntryValid = validateInput(itemDetails))
    }

    private fun validateInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean{
        return with(uiState){
            nome.isNotBlank() && preco.isNotBlank() && quantidade>0
        }
    }

    suspend fun saveItem(){
        if(validateInput()){
            itemsRepository.insertItem(itemUiState.itemDetails.toItem())
        }
    }
}

data class ItemUiState(
    val itemDetails: ItemDetails = ItemDetails(),
    val isEntryValid: Boolean = false,
)

data class ItemDetails(
    val id: Int = 0,
    val nome: String = "",
    val preco: String = "",
    val quantidade: Int = 0
)

fun ItemDetails.toItem(): Item = Item(
    id = id,
    nome = nome,
    preco = preco,
    quantidade = quantidade
)

fun Item.toItemUiState(isEntryValid: Boolean = false) : ItemUiState = ItemUiState(
    itemDetails = this.toItemDetails(),
    isEntryValid = isEntryValid
)

fun Item.toItemDetails(): ItemDetails = ItemDetails(
    id = id,
    nome = nome,
    preco = preco,
    quantidade = quantidade
)
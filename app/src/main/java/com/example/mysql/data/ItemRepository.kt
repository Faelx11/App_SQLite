package com.example.mysql.data

import kotlinx.coroutines.flow.Flow

interface ItemRepository{
    suspend fun insertItem(item: Item)
    suspend fun updateItem(item: Item)
    suspend fun deleteItem(item: Item)
    fun getItemStream(id: Int): Flow<Item?>
    fun getAllItemsStream(): Flow<List<Item>>
}
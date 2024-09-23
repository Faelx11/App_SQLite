package com.example.mysql.data

import kotlinx.coroutines.flow.Flow

class OfflineItemRepository(private val itemDao: ItemDao):ItemRepository{
    override fun getItemStream(id: Int): Flow<Item?> = itemDao.getItem(id)
    override fun getAllItemsStream():Flow<List<Item>> = itemDao.getAllItems()
    override suspend fun insertItem(item:Item) = itemDao.insert(item)
    override suspend fun deleteItem(item:Item) = itemDao.delete(item)
    override suspend fun updateItem(item:Item) = itemDao.update(item)

}
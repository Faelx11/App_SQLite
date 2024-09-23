package com.example.mysql.data

import android.content.Context

interface AppContainer{
    val itemsRepository: ItemRepository
}

class AppDataContainer(private val context: Context): AppContainer{
    override val itemsRepository: ItemRepository by lazy {
        OfflineItemRepository(ItemDatabase
            .getDatabase(context).itemDao())
    }
}
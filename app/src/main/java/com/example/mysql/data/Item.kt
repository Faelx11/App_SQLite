package com.example.mysql.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "itens")
data class Item(
    @PrimaryKey (autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val preco: String,
    val quantidade: Int
)

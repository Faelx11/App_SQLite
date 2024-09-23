package com.example.mysql.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mysql.MercadinhoApplication
import com.example.mysql.ui.home.HomeViewModel
import com.example.mysql.ui.item.ItemDetailsViewModel
import com.example.mysql.ui.item.ItemEditViewModel
import com.example.mysql.ui.item.ItemEntryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory{
        initializer {
            HomeViewModel(mercadinhoApplication().container.itemsRepository)
        }

        initializer {
            ItemEntryViewModel(mercadinhoApplication().container.itemsRepository)
        }

        initializer {
            ItemDetailsViewModel(
                this.createSavedStateHandle(),
                mercadinhoApplication().container.itemsRepository
            )
        }

        initializer {
            ItemEditViewModel(
                this.createSavedStateHandle(),
                mercadinhoApplication().container.itemsRepository
            )
        }
    }
}

fun CreationExtras.mercadinhoApplication(): MercadinhoApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as MercadinhoApplication)
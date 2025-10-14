package com.kriscg.laboratorio8.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kriscg.laboratorio8.data.room.AppDatabase

class LocationsViewModelFactory(
    private val database: AppDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocationsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LocationsViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
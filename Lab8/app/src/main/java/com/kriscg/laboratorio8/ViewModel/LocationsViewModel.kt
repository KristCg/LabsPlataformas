package com.kriscg.laboratorio8.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kriscg.laboratorio8.data.room.AppDatabase
import com.kriscg.laboratorio8.data.room.entity.LocationEntity
import com.kriscg.laboratorio8.models.Location
import com.kriscg.laboratorio8.data.LocationDb
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class LocationsViewModel(
    private val database: AppDatabase
) : ViewModel() {

    private val _state = MutableStateFlow(Estados<List<Location>>(isLoading = true))
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            populateLocationsIfEmpty()
            fetchLocations()
        }
    }

    private suspend fun populateLocationsIfEmpty() {
        val dao = database.locationDao()
        if (dao.getAllLocations().isEmpty()) {
            val locationsToInsert = LocationDb.getAllLocations().map { location ->
                LocationEntity(
                    name = location.name,
                    type = location.type,
                    dimension = location.dimension
                )
            }
            dao.insertAll(locationsToInsert)
        }
    }

    fun fetchLocations() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, isError = false, data = null) }

            delay(2000)

            try {
                val dao = database.locationDao()
                val locations = dao.getAllLocations().map {
                    Location(it.id, it.name, it.type, it.dimension)
                }

                _state.update { it.copy(isLoading = false, isError = false, data = locations) }

            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, isError = true, data = null) }
            }
        }
    }

    fun retry() = fetchLocations()
}




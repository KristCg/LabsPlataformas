package com.kriscg.laboratorio8.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kriscg.laboratorio8.models.Location
import com.kriscg.laboratorio8.data.LocationDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationsViewModel : ViewModel() {

    private val _state = MutableStateFlow(Estados<List<Location>>(isLoading = true))
    val state = _state.asStateFlow()

    init {
        fetchLocations()
    }

    fun fetchLocations() {
        viewModelScope.launch {
            _state.update { it.copy(
                isLoading = true,
                isError = false,
                data = null
            ) }

            delay(4_000)

            val rnd = (1..10).random()
            if (rnd % 2 == 0) {
                val locations = LocationDb.getAllLocations()
                _state.update { it.copy(
                    isLoading = false,
                    isError = false,
                    data = locations
                ) }
            } else {
                _state.update { it.copy(
                    isLoading = false,
                    isError = true,
                    data = null
                ) }
            }
        }
    }


    fun retry() = fetchLocations()
}


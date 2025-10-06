package com.kriscg.laboratorio8.ViewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kriscg.laboratorio8.models.Location
import com.kriscg.laboratorio8.data.LocationDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class LocationDetailViewModel(
    private val locationId: Int
) : ViewModel() {

    private val _state = MutableStateFlow(Estados<Location>(isLoading = true))
    val state = _state.asStateFlow()

    init { fetchLocation() }

    fun fetchLocation() {
        viewModelScope.launch {
            _state.value = Estados(isLoading = true)
            delay(2000)

            val rnd = (1..10).random()
            if (rnd % 2 == 0) {
                val location = LocationDb.getLocationById(locationId)
                _state.value = Estados(isLoading = false, data = location)
            } else {
                _state.value = Estados(isLoading = false, isError = true)
            }
        }
    }

    fun retry() = fetchLocation()
}


package com.kriscg.laboratorio8.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kriscg.laboratorio8.models.Character
import com.kriscg.laboratorio8.data.CharacterDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharactersViewModel : ViewModel() {

    private val _state = MutableStateFlow(Estados<List<Character>>(isLoading = true))
    val state = _state.asStateFlow()

    init {
        fetchCharacters()
    }

    fun fetchCharacters() {
        viewModelScope.launch {
            _state.update { it.copy(
                isLoading = true,
                isError = false,
                data = null
            ) }

            delay(4_000)

            val rnd = (1..10).random()
            if (rnd % 2 == 0) {
                val characters = CharacterDb.getAllCharacters()
                _state.update { it.copy(
                    isLoading = false,
                    isError = false,
                    data = characters
                ) }
            } else {
                _state.update { it.copy(
                    isLoading = false,
                    isError = true,
                    data = null) }
            }
        }
    }

    fun retry() = fetchCharacters()
}

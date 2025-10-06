package com.kriscg.laboratorio8.ViewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.kriscg.laboratorio8.data.CharacterDb
import com.kriscg.laboratorio8.models.Character

class CharacterDetailViewModel(
    private val characterId: Int
) : ViewModel() {

    private val _state = MutableStateFlow(Estados<Character>(isLoading = true))
    val state = _state.asStateFlow()

    init { fetchCharacter() }

    fun fetchCharacter() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, isError = false, data = null) }
            delay(2000)
            val rnd = (1..10).random()
            if (rnd % 2 == 0) {
                val character = CharacterDb.getCharacterById(characterId)
                _state.update { it.copy(isLoading = false, isError = false, data = character) }
            } else {
                _state.update { it.copy(isLoading = false, isError = true, data = null) }
            }
        }
    }

    fun retry() = fetchCharacter()
}

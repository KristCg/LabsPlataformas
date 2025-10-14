package com.kriscg.laboratorio8.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kriscg.laboratorio8.models.Character
import com.kriscg.laboratorio8.data.CharacterDb
import com.kriscg.laboratorio8.data.room.AppDatabase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.kriscg.laboratorio8.data.room.entity.CharacterEntity


class CharactersViewModel(
    private val database: AppDatabase
) : ViewModel() {

    private val _state = MutableStateFlow(Estados<List<Character>>(isLoading = true))
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            populateCharactersIfEmpty()
            fetchCharacters()
        }
    }

    private suspend fun populateCharactersIfEmpty() {
        val dao = database.characterDao()
        if (dao.getAllCharacters().isEmpty()) {
            val charactersToInsert = CharacterDb.getAllCharacters().map { character ->
                CharacterEntity(
                    name = character.name,
                    status = character.status,
                    species = character.species,
                    gender = character.gender,
                    image = character.image
                )
            }
            dao.insertAll(charactersToInsert)
        }
    }

    fun fetchCharacters() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, isError = false, data = null) }

            delay(4_000) // simulando carga

            try {
                val dao = database.characterDao()
                val characters = dao.getAllCharacters().map { entity ->
                    Character(
                        id = entity.id,
                        name = entity.name,
                        status = entity.status,
                        species = entity.species,
                        gender = entity.gender,
                        image = entity.image
                    )
                }

                _state.update { it.copy(isLoading = false, isError = false, data = characters) }

            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, isError = true, data = null) }
            }
        }
    }

    fun retry() = fetchCharacters()
}


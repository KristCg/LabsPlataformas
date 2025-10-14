package com.kriscg.laboratorio8.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kriscg.laboratorio8.data.room.entity.CharacterEntity
import com.kriscg.laboratorio8.models.Character

@Dao
interface CharacterDao{
    @Query("SELECT * FROM Characters")
    suspend fun getAllCharacters(): List<CharacterEntity>

    @Query("SELECT * FROM Characters WHERE id = :id")
    suspend fun getAllCharactersById(id: Int): CharacterEntity?

    @Insert
    suspend fun insertAll(characters: List<CharacterEntity>)

    @Update
    suspend fun updateLocation(location: CharacterEntity)

    @Delete
    suspend fun deleteLocation(location: CharacterEntity)

    @Query("DELETE FROM Characters")
    suspend fun deleteAll()
}
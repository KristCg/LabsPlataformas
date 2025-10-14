package com.kriscg.laboratorio8.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kriscg.laboratorio8.data.room.entity.LocationEntity


@Dao
interface LocationDao{
    @Query("SELECT * FROM Locations")
    suspend fun getAllLocations(): List<LocationEntity>

    @Query("SELECT * FROM Locations WHERE id = :id ")
    suspend fun getAllLocations(id: Int): LocationEntity?

    @Insert
    suspend fun insertAll(locations: List<LocationEntity>)

    @Update
    suspend fun updateLocation(location: LocationEntity)

    @Delete
    suspend fun deleteLocation(location: LocationEntity)

    @Query("DELETE FROM Locations")
    suspend fun deleteAll()
}

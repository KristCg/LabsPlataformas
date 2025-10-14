package com.kriscg.laboratorio8.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kriscg.laboratorio8.data.room.dao.CharacterDao
import com.kriscg.laboratorio8.data.room.dao.LocationDao
import com.kriscg.laboratorio8.data.room.entity.CharacterEntity
import com.kriscg.laboratorio8.data.room.entity.LocationEntity

//Kristel Castillo -241294

@Database(entities = [CharacterEntity::class, LocationEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun characterDao(): CharacterDao
    abstract fun locationDao(): LocationDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "rickandmorty_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
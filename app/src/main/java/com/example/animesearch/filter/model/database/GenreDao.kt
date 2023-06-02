package com.example.animesearch.filter.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenreList(genres: List<GenreEntity>)

    @Query("SELECT * FROM GenreEntity")
    suspend fun getGenres(): List<GenreEntity>
}
package com.example.animesearch.filter.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface GenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenreList(genres: List<GenreEntity>)

    @Query("SELECT * FROM GenreEntity")
    fun getGenres(): Single<List<GenreEntity>>
}
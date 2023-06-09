package com.example.animesearch.helper

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.animesearch.filter.model.database.GenreDao
import com.example.animesearch.filter.model.database.GenreEntity

@Database(
    version = 1,
    entities = [
        GenreEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getGenreDao(): GenreDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, "anime_search.db").build()
    }
}
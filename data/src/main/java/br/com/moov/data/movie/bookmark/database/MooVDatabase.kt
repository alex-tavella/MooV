package br.com.moov.data.movie.bookmark.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.moov.data.movie.bookmark.database.dao.MoviesDao
import br.com.moov.data.movie.bookmark.database.entity.MovieEntity

@Database(version = MooVDatabase.DB_VERSION,
    entities = [MovieEntity::class])
abstract class MooVDatabase : RoomDatabase() {

  abstract fun moviesDao(): MoviesDao

  companion object {

    private const val DB_NAME = "moov"
    const val DB_VERSION = 1

    fun create(context: Context): MooVDatabase {
      return Room.databaseBuilder(context, MooVDatabase::class.java, DB_NAME)
          .build()
    }
  }
}

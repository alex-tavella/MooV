package br.com.moov.data.movie.bookmark.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.moov.data.movie.bookmark.database.entity.MovieEntity

@Dao
interface MoviesDao {

  @Query("SELECT * FROM movie")
  fun getAll(): List<MovieEntity>

  @Query("SELECT * FROM movie WHERE id == :movieId")
  fun get(movieId: Int): MovieEntity?

  @Query("SELECT * FROM movie WHERE id IN (:movieIds)")
  fun loadAllByIds(movieIds: IntArray): List<MovieEntity>

  @Insert
  fun insert(movie: MovieEntity): Long

  @Query("DELETE FROM movie WHERE id = :movieId")
  fun delete(movieId: Int): Int
}
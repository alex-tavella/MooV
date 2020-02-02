package br.com.moov.data.movie.bookmark

import br.com.moov.data.movie.bookmark.database.MooVDatabase
import br.com.moov.data.movie.bookmark.database.entity.MovieEntity

class LocalDataSource(private val database: MooVDatabase) : BookmarkDataSource {
  override suspend fun getBookmarks(): List<MovieEntity> {
    return database.moviesDao().getAll()
  }

  override suspend fun getBookmarks(ids: IntArray): List<MovieEntity> {
    return database.moviesDao().loadAllByIds(ids)
  }

  override suspend fun isMovieBookmarked(movieId: Int): Boolean {
    return database.moviesDao().get(movieId) != null
  }

  override suspend fun bookmarkMovie(movie: MovieEntity) {
    database.moviesDao().insert(movie)
  }

  override suspend fun unbookmarkMovie(movieId: Int) {
    database.moviesDao().delete(movieId)
  }
}
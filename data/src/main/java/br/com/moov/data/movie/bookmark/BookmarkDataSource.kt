package br.com.moov.data.movie.bookmark

import br.com.moov.data.movie.bookmark.database.entity.MovieEntity

interface BookmarkDataSource {
    suspend fun getBookmarks(): List<MovieEntity>
    suspend fun getBookmarks(ids: IntArray): List<MovieEntity>
    suspend fun isMovieBookmarked(movieId: Int): Boolean
    suspend fun bookmarkMovie(movie: MovieEntity)
    suspend fun unbookmarkMovie(movieId: Int)
}

package br.com.bookmark.movie.data.local

import br.com.bookmark.movie.data.local.dao.MovieBookmarksDao
import br.com.bookmark.movie.data.local.entity.MovieBookmark

class TestMovieBookmarksDao(
    initialMovies: List<MovieBookmark> = emptyList()
) : MovieBookmarksDao {

    private val movies = initialMovies.toMutableList()

    override suspend fun getAll(): List<MovieBookmark> {
        return movies.toList()
    }

    override suspend fun get(movieId: Int): MovieBookmark? {
        return movies.find { it.id == movieId }
    }

    override suspend fun loadAllByIds(movieIds: IntArray): List<MovieBookmark> {
        return movies.filter { movieIds.contains(it.id) }
    }

    override suspend fun insert(movie: MovieBookmark): Long {
        movies.add(movie)
        return movies.size.toLong()
    }

    override suspend fun delete(movieId: Int): Int {
        movies.removeIf { it.id == movieId }
        return movies.size
    }
}
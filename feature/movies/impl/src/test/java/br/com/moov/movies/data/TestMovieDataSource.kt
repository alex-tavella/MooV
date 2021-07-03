package br.com.moov.movies.data

import br.com.moov.movies.domain.Movie

class TestMovieDataSource(
    private val movies: List<Movie> = emptyList(),
    private val pageSize: Int = 3
) : MovieDataSource {
    override suspend fun getMovies(page: Int): List<Movie> {
        return movies.take(pageSize)
    }
}

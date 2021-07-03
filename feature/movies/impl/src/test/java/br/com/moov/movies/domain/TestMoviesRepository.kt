package br.com.moov.movies.domain 

class TestMoviesRepository(
    private val movies: List<Movie> = emptyList(),
    private val pageSize: Int = 3
) : MoviesRepository {
    override suspend fun getMovies(page: Int): List<Movie> {
        return movies.take(pageSize)
    }
}

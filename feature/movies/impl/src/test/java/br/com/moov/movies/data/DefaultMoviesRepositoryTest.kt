package br.com.moov.movies.data

import br.com.moov.movies.domain.Movie
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class DefaultMoviesRepositoryTest {

    private val movieDataSource = TestMovieDataSource()
    private val repository = DefaultMoviesRepository(movieDataSource)

    @Test
    fun getMovies_returnsMoviesFromDataSource() = runBlocking {
        val actual = repository.getMovies(1)

        val expected = emptyList<Movie>()
        assertEquals(expected, actual)
    }
}

package br.com.moov.movies.domain

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetMoviesTest {

    private val getMovies = GetMovies(TestMoviesRepository())

    @Test
    fun invoke_returnsMoviesFromRepository() = runBlocking {
        val actual = getMovies(1)

        val expected = listOf<Movie>()
        assertEquals(expected, actual)
    }
}

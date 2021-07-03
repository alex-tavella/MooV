package br.com.moov.moviedetails.domain

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class GetMovieDetailTest {
    private val movies: List<MovieDetail> = listOf(
        MovieDetail(
            id = 123,
            title = "Lord of the Rings"
        )
    )
    private val getMovieDetail = GetMovieDetail(TestMovieDetailRepository(movies))

    @Test
    fun getMovieDetail_exists_returnsMovieDetail() = runBlocking {
        val actual = getMovieDetail(123)

        assertEquals(MovieDetail(123, "Lord of the Rings"), actual)
    }

    @Test
    fun getMovieDetail_notFound_returnsNull() = runBlocking {
        val actual = getMovieDetail(456)

        assertNull(actual)
    }
}

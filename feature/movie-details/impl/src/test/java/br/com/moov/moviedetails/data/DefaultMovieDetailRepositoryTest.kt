package br.com.moov.moviedetails.data

import br.com.moov.moviedetails.domain.MovieDetail
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class DefaultMovieDetailRepositoryTest {
    private val movies: List<MovieDetail> = listOf(
        MovieDetail(
            id = 123,
            title = "Lord of the Rings"
        )
    )
    private val repository = DefaultMovieDetailRepository(TestMovieDetailDataSource(movies))

    @Test
    fun getMovieDetail_exists_returnsMovieDetail() = runBlocking {
        val actual = repository.getMovieDetail(123)

        assertEquals(MovieDetail(123, "Lord of the Rings"), actual)
    }

    @Test
    fun getMovieDetail_notFound_returnsNull() = runBlocking {
        val actual = repository.getMovieDetail(456)

        assertNull(actual)
    }
}

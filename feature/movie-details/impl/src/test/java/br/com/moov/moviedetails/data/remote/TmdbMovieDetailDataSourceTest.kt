package br.com.moov.moviedetails.data.remote

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class TmdbMovieDetailDataSourceTest {

    private val apiMovies = listOf(
        TmdbMovieDetail(123),
        TmdbMovieDetail(456),
    )

    private val movieDetailApi = TestTMDBMovieDetailApi(apiMovies)

    private val imageUrlResolver = TestImageUrlResolver()

    private val dataSource =
        TmdbMovieDetailDataSource(movieDetailApi, TmdbMovieMapper(imageUrlResolver))

    @Test
    fun getMovieDetail_apiResponds_returnsFromApi() = runBlocking {
        val actual = dataSource.getMovieDetail(123)

        assertEquals(123, actual?.id)
    }

    @Test
    fun getMovieDetail_apiFails_returnsFromApi() = runBlocking {
        val actual = dataSource.getMovieDetail(789)

        assertNull(actual)
    }
}

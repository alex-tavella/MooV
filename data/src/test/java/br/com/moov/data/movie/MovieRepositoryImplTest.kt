package br.com.moov.data.movie

import br.com.moov.data.movie.bookmark.BookmarkDataSource
import br.com.moov.data.movie.tmdb.TMDBMovie
import br.com.moov.data.movie.tmdb.TMDBMovieDetail
import br.com.moov.data.test.LocalDataFactory
import br.com.moov.data.test.RemoteDataFactory
import br.com.moov.domain.movie.MovieRepository
import br.com.moov.domain.test.DomainDataFactory
import br.com.moov.test.DataFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Fail
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieRepositoryImplTest {

    private val mockedMovieDataSource: MovieDataSource = mock()

    private val mockedBookmarkDataSource: BookmarkDataSource = mock()

    private val mockedMapper: MovieMapper = mock()

    private lateinit var movieRepositoryImpl: MovieRepository

    @Before
    @Throws(Exception::class)
    fun setup() {
        movieRepositoryImpl = MovieRepositoryImpl(
            mockedMovieDataSource, mockedBookmarkDataSource,
            mockedMapper
        )
    }

    @Test
    fun `get popular movies`() {
        runBlocking {
            // Given
            val responseSize = 20
            val sourceMovieList = RemoteDataFactory.newTMDBMoviesList(responseSize)
            whenever(mockedMovieDataSource.getMovies(any(), any(), any())).thenReturn(
                sourceMovieList
            )
            whenever(mockedBookmarkDataSource.getBookmarks(any()))
                .thenReturn(LocalDataFactory.newMovieList())
            whenever(mockedMovieDataSource.getImageConfigs()).thenReturn(
                RemoteDataFactory.newImageConfigurations()
            )

            val expectedMovie = DomainDataFactory.newMovie()
            whenever(mockedMapper.map(any<TMDBMovie>(), any())).thenReturn(expectedMovie)

            // When
            val page = DataFactory.randomInt()
            val popularMovies = movieRepositoryImpl.getPopularMovies(page)

            // Then
            assert(popularMovies.size == sourceMovieList.size)
            popularMovies.forEach { assert(it == expectedMovie) }
            verify(mockedMovieDataSource).getImageConfigs()
            val pageCaptor = argumentCaptor<Int>()
            val sortByCaptor = argumentCaptor<String>()
            val voteCountCaptor = argumentCaptor<Int>()
            verify(mockedMovieDataSource).getMovies(
                pageCaptor.capture(), sortByCaptor.capture(),
                voteCountCaptor.capture()
            )
            assert(pageCaptor.firstValue == page)
            assert(sortByCaptor.firstValue == MovieRepositoryImpl.SORT_ORDER_POPULARITY)
            assert(voteCountCaptor.firstValue == MovieRepositoryImpl.THRESHOLD_VOTE_COUNT)
            verify(mockedMapper, times(responseSize)).map(any<TMDBMovie>(), any())
        }
    }

    @Test
    fun `get popular movies error`() {
        runBlocking {
            // Given
            whenever(mockedMovieDataSource.getMovies(any(), any(), any())).thenThrow(
                IllegalStateException()
            )
            whenever(mockedMovieDataSource.getImageConfigs()).thenReturn(
                RemoteDataFactory.newImageConfigurations()
            )

            whenever(mockedMapper.map(any<TMDBMovie>(), any()))
                .thenReturn(DomainDataFactory.newMovie())

            val page = DataFactory.randomInt()
            try {
                // When
                movieRepositoryImpl.getPopularMovies(page)
                Fail.failBecauseExceptionWasNotThrown<Any>(IllegalStateException::class.java)
            } catch (e: Exception) {
                // Then
                assert(e is IllegalStateException)
                verify(mockedMovieDataSource, never()).getImageConfigs()
                val pageCaptor = argumentCaptor<Int>()
                val sortByCaptor = argumentCaptor<String>()
                val voteCountCaptor = argumentCaptor<Int>()
                verify(mockedMovieDataSource).getMovies(
                    pageCaptor.capture(), sortByCaptor.capture(),
                    voteCountCaptor.capture()
                )
                assert(pageCaptor.firstValue == page)
                assert(sortByCaptor.firstValue == MovieRepositoryImpl.SORT_ORDER_POPULARITY)
                assert(voteCountCaptor.firstValue == MovieRepositoryImpl.THRESHOLD_VOTE_COUNT)
                verify(mockedMapper, never()).map(any<TMDBMovie>(), any())
            }
        }
    }

    @Test
    fun `get popular config error`() {
        runBlocking {
            // Given
            val responseSize = 20
            whenever(mockedMovieDataSource.getMovies(any(), any(), any()))
                .thenReturn(RemoteDataFactory.newTMDBMoviesList(responseSize))
            whenever(mockedMovieDataSource.getImageConfigs()).thenThrow(IllegalStateException())

            whenever(mockedMapper.map(any<TMDBMovie>(), any()))
                .thenReturn(DomainDataFactory.newMovie())

            val page = DataFactory.randomInt()
            try {
                // When
                movieRepositoryImpl.getPopularMovies(page)
                Fail.failBecauseExceptionWasNotThrown<Any>(IllegalStateException::class.java)
            } catch (e: Exception) {
                // Then
                assert(e is IllegalStateException)
                verify(mockedMovieDataSource).getImageConfigs()
                val pageCaptor = argumentCaptor<Int>()
                val sortByCaptor = argumentCaptor<String>()
                val voteCountCaptor = argumentCaptor<Int>()
                verify(mockedMovieDataSource).getMovies(
                    pageCaptor.capture(), sortByCaptor.capture(),
                    voteCountCaptor.capture()
                )
                assert(pageCaptor.firstValue == page)
                assert(sortByCaptor.firstValue == MovieRepositoryImpl.SORT_ORDER_POPULARITY)
                assert(voteCountCaptor.firstValue == MovieRepositoryImpl.THRESHOLD_VOTE_COUNT)
                verify(mockedMapper, never()).map(any<TMDBMovie>(), any())
            }
        }
    }

    @Test
    fun `get movie detail unbookmarked`() {
        runBlocking {
            // Given
            whenever(mockedMovieDataSource.getMovieDetail(any()))
                .thenReturn(RemoteDataFactory.newTMDBMovieDetail())
            whenever(mockedBookmarkDataSource.isMovieBookmarked(any()))
                .thenReturn(false)
            whenever(mockedMovieDataSource.getImageConfigs())
                .thenReturn(RemoteDataFactory.newImageConfigurations())
            val expected = DomainDataFactory.newMovieDetail()
            whenever(mockedMapper.map(any<TMDBMovieDetail>(), any())).thenReturn(expected)

            // When
            val movieId = DataFactory.randomInt()
            val result = movieRepositoryImpl.getMovieDetail(movieId)

            // Then
            assertEquals(expected, result)
        }
    }

    @Test
    fun `get movie detail bookmarked`() {
        runBlocking {
            // Given
            whenever(mockedMovieDataSource.getMovieDetail(any()))
                .thenReturn(RemoteDataFactory.newTMDBMovieDetail())
            whenever(mockedBookmarkDataSource.isMovieBookmarked(any()))
                .thenReturn(true)
            whenever(mockedMovieDataSource.getImageConfigs())
                .thenReturn(RemoteDataFactory.newImageConfigurations())
            val expected = DomainDataFactory.newMovieDetail()
            whenever(mockedMapper.map(any<TMDBMovieDetail>(), any())).thenReturn(expected)

            // When
            val movieId = DataFactory.randomInt()
            val result = movieRepositoryImpl.getMovieDetail(movieId)

            // Then
            assert(result.isBookmarked)
            assert(result.title == expected.title)
            assert(result.voteAverage == expected.voteAverage)
            assert(result.releaseDate == expected.releaseDate)
            assert(result.popularity == expected.popularity)
            assert(result.overview == expected.overview)
            assert(result.genres == expected.genres)
            assert(result.backdropUrl == expected.backdropUrl)
            assert(result.posterUrl == expected.posterUrl)
            assert(result.originalLanguage == expected.originalLanguage)
        }
    }
}

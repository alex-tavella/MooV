package br.com.moov.data.movie

import br.com.moov.data.movie.tmdb.TMDBDApi
import br.com.moov.data.movie.tmdb.TMDBMovie
import br.com.moov.data.test.RemoteDataFactory
import br.com.moov.test.DataFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.mock.Calls

@RunWith(JUnit4::class)
class TMDBMovieDataSourceTest {

  private val tmdbApi: TMDBDApi = mock()

  private lateinit var tmdbMovieDataSource: MovieDataSource

  @Before
  @Throws(Exception::class)
  fun setUp() {
    tmdbMovieDataSource = TMDBMovieDataSource(tmdbApi)
  }

  @Test
  fun `get popular movies`() {
    // Given
    val movieDiscoverResponse = RemoteDataFactory.newMovieResponse()
    whenever(tmdbApi.discoverMovies(any(), any(), any()))
        .thenReturn(Calls.response(movieDiscoverResponse))

    // When
    val page = 1
    val sortBy = "popularity.desc"
    val voteCount = 100
    val movies = runBlocking {
      tmdbMovieDataSource.getMovies(page, sortBy, voteCount)
    }

    // Then
    assert(movies == movieDiscoverResponse.results)
    val pageCaptor = argumentCaptor<Int>()
    val sortByCaptor = argumentCaptor<String>()
    val voteCountCaptor = argumentCaptor<Int>()
    verify(tmdbApi).discoverMovies(pageCaptor.capture(), sortByCaptor.capture(),
        voteCountCaptor.capture())
    assert(pageCaptor.firstValue == page)
    assert(sortByCaptor.firstValue == sortBy)
    assert(voteCountCaptor.firstValue == voteCount)
  }

  @Test
  fun `get popular movies empty response`() {
    // Given
    whenever(tmdbApi.discoverMovies(any(), any(), any()))
        .thenReturn(Calls.response(RemoteDataFactory
            .newMovieResponse().copy(results = emptyList())))

    // When
    val page = 1
    val sortBy = "popularity.desc"
    val voteCount = 100
    val movies = runBlocking {
      tmdbMovieDataSource.getMovies(page, sortBy, voteCount)
    }

    // Then
    assert(movies == emptyList<TMDBMovie>())
    val pageCaptor = argumentCaptor<Int>()
    val sortByCaptor = argumentCaptor<String>()
    val voteCountCaptor = argumentCaptor<Int>()
    verify(tmdbApi).discoverMovies(pageCaptor.capture(), sortByCaptor.capture(),
        voteCountCaptor.capture())
    assert(pageCaptor.firstValue == page)
    assert(sortByCaptor.firstValue == sortBy)
    assert(voteCountCaptor.firstValue == voteCount)
  }

  @Test
  fun `get popular movies error`() {
    // Given
    whenever(tmdbApi.discoverMovies(any(), any(), any()))
        .thenThrow(IllegalStateException())

    runBlocking {
      val page = 1
      val sortBy = "popularity.desc"
      val voteCount = 100
      try {
        tmdbMovieDataSource.getMovies(page, sortBy, voteCount)
        Fail.failBecauseExceptionWasNotThrown(IllegalStateException::class.java)
      } catch (e: Exception) {
        //Then
        assert(e is IllegalStateException)
      } finally {
        val pageCaptor = argumentCaptor<Int>()
        val sortByCaptor = argumentCaptor<String>()
        val voteCountCaptor = argumentCaptor<Int>()
        verify(tmdbApi).discoverMovies(pageCaptor.capture(), sortByCaptor.capture(),
            voteCountCaptor.capture())
        assert(pageCaptor.firstValue == page)
        assert(sortByCaptor.firstValue == sortBy)
        assert(voteCountCaptor.firstValue == voteCount)
      }
    }
  }

  @Test
  fun `get movie details`() {
    // Given
    whenever(tmdbApi.getMovie(any()))
        .thenReturn(Calls.response(RemoteDataFactory.newTMDBMovieDetail()))

    runBlocking {
      // When
      val movieId = DataFactory.randomInt()
      tmdbMovieDataSource.getMovieDetail(movieId)

      //Then
      val idCaptor = argumentCaptor<Int>()
      verify(tmdbApi).getMovie(idCaptor.capture())
      assert(idCaptor.firstValue == movieId)
    }
  }

  @Test
  fun `get movie details error`() {
    // Given
    whenever(tmdbApi.getMovie(any())).thenThrow(IllegalStateException())

    runBlocking {
      // When
      val movieId = DataFactory.randomInt()
      val result = kotlin.runCatching {
        tmdbMovieDataSource.getMovieDetail(movieId)
      }

      //Then
      val idCaptor = argumentCaptor<Int>()
      verify(tmdbApi).getMovie(idCaptor.capture())
      assert(idCaptor.firstValue == movieId)
      assert(result.isFailure)
      assert(!result.isSuccess)
      assert(result.getOrNull() == null)
      assert(result.exceptionOrNull() is IllegalStateException)
    }
  }
}
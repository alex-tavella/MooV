package br.com.moov.app.movies

import br.com.moov.app.movies.MoviesUiEvent.EnterScreenUiEvent
import br.com.moov.app.movies.MoviesUiEvent.FinishedScrollingUiEvent
import br.com.moov.domain.movie.MovieBookmarkInteractor
import br.com.moov.domain.movie.MoviesInteractor
import br.com.moov.domain.test.DomainDataFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MoviesViewModelTest {

  private val moviesInteractor: MoviesInteractor = mock()

  private val bookmarkInteractor: MovieBookmarkInteractor = mock()

  private lateinit var viewModel: MoviesViewModel

  @Before
  @Throws(Exception::class)
  fun setUp() {
    viewModel = MoviesViewModel(moviesInteractor, bookmarkInteractor)
  }

  @Test
  fun `process enter event`() {
    runBlocking {
      // Given
      val newMovieList = DomainDataFactory.newMovieList(20)
      whenever(moviesInteractor.getPopularMovies(any())).thenReturn(newMovieList)

      // When
      var uiModel: MoviesUiModel? = null
      var updateCount = 0
      val job = launch(Dispatchers.Unconfined) {
        viewModel.observe(this) {
          updateCount++
          uiModel = it
        }
      }
      viewModel.uiEvent(EnterScreenUiEvent)

      delay(1000)
      job.cancelAndJoin()

      // Then
      assertEquals(2, updateCount)
      uiModel?.apply {
        assertEquals(false, loading)
        assertEquals(newMovieList, this.movies)
      }
    }
  }

  @Test
  fun `process scroll event`() {
    runBlocking {
      // Given
      val newMovieList = DomainDataFactory.newMovieList(20)
      whenever(moviesInteractor.getPopularMovies(any())).thenReturn(newMovieList)

      // When
      var uiModel: MoviesUiModel? = null
      var updateCount = 0
      val job = launch(Dispatchers.Unconfined) {
        viewModel.observe(this) {
          updateCount++
          uiModel = it
        }
      }
      viewModel.uiEvent(EnterScreenUiEvent)
      viewModel.uiEvent(FinishedScrollingUiEvent)

      delay(1000)
      job.cancelAndJoin()

      // Then
      assertEquals(4, updateCount)
      uiModel?.apply {
        assertEquals(false, loading)
        assertEquals(newMovieList.apply { addAll(newMovieList) }, movies)
      }
    }
  }

  @Test
  fun `process scroll twice`() {
    runBlocking {
      // Given
      val newMovieList = DomainDataFactory.newMovieList(20)
      whenever(moviesInteractor.getPopularMovies(any())).thenReturn(newMovieList)

      // When
      var uiModel: MoviesUiModel? = null
      var updateCount = 0
      val job = launch(Dispatchers.Unconfined) {
        viewModel.observe(this) {
          updateCount++
          uiModel = it
        }
      }
      viewModel.uiEvent(EnterScreenUiEvent)
      viewModel.uiEvent(FinishedScrollingUiEvent)
      viewModel.uiEvent(FinishedScrollingUiEvent)

      delay(1000)
      job.cancelAndJoin()

      // Then
      assertEquals(6, updateCount)
      uiModel?.apply {
        assertEquals(false, loading)
        val expected = newMovieList.apply {
          addAll(newMovieList)
          addAll(newMovieList.take(10))
        }
        assertEquals(expected, movies)
        assertEquals(MoviesViewModel.LIMIT_MOVIES, movies.size)
      }
    }
  }

  @Test
  fun `process scroll repeatedly`() {
    runBlocking {
      // Given
      val newMovieList = DomainDataFactory.newMovieList(20)
      whenever(moviesInteractor.getPopularMovies(any())).thenReturn(newMovieList)

      // When
      var uiModel: MoviesUiModel? = null
      var updateCount = 0
      val job = launch(Dispatchers.Unconfined) {
        viewModel.observe(this) {
          updateCount++
          uiModel = it
        }
      }
      viewModel.uiEvent(EnterScreenUiEvent)
      repeat(5) { viewModel.uiEvent(FinishedScrollingUiEvent) }

      delay(1000)
      job.cancelAndJoin()

      // Then
      assertEquals(6, updateCount)
      uiModel?.apply {
        assertEquals(false, loading)
        val expected = newMovieList.apply {
          addAll(newMovieList)
          addAll(newMovieList.take(10))
        }
        assertEquals(expected, movies)
        assertEquals(MoviesViewModel.LIMIT_MOVIES, movies.size)
      }
    }
  }

  @Test
  fun `process enter screen loads more than 50`() {
    runBlocking {
      // Given
      val newMovieList = DomainDataFactory.newMovieList(100)
      whenever(moviesInteractor.getPopularMovies(any())).thenReturn(newMovieList)

      // When
      var uiModel: MoviesUiModel? = null
      var updateCount = 0
      val job = launch(Dispatchers.Unconfined) {
        viewModel.observe(this) {
          updateCount++
          uiModel = it
        }
      }
      viewModel.uiEvent(EnterScreenUiEvent)

      delay(1000)
      job.cancelAndJoin()

      // Then
      assertEquals(2, updateCount)
      uiModel?.apply {
        assertEquals(false, loading)
        assertEquals(MoviesViewModel.LIMIT_MOVIES, movies.size)
        val expected = newMovieList.take(MoviesViewModel.LIMIT_MOVIES)
        assertEquals(expected, movies)
      }
    }
  }

  @Test
  fun `process enter screen twice`() {
    runBlocking {
      // Given
      val newMovieList = DomainDataFactory.newMovieList(20)
      whenever(moviesInteractor.getPopularMovies(any())).thenReturn(newMovieList)
      whenever(bookmarkInteractor.getBookmarkedMovies(any())).thenReturn(newMovieList)

      // When
      var uiModel: MoviesUiModel? = null
      var updateCount = 0
      val job = launch(Dispatchers.Unconfined) {
        viewModel.observe(this) {
          updateCount++
          uiModel = it
        }
      }
      viewModel.uiEvent(EnterScreenUiEvent)
      viewModel.uiEvent(EnterScreenUiEvent)

      delay(1000)
      job.cancelAndJoin()

      // Then
      assertEquals(3, updateCount)
      uiModel?.apply {
        assertEquals(false, loading)
        assertEquals(20, movies.size)
        assertEquals(newMovieList, movies)
      }
    }
  }
}
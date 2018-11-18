package br.com.moov.domain.movie

import br.com.moov.domain.test.DomainDataFactory
import br.com.moov.test.DataFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieDetailInteractorImplTest {

  private val repository: MovieRepository = mock()

  private lateinit var interactor: MovieDetailInteractor

  @Before
  fun setup() {
    interactor = MovieDetailInteractorImpl(repository)
  }

  @Test
  fun `get movie details`() {
    runBlocking {
      // Given
      val expected = DomainDataFactory.newMovieDetail()
      whenever(repository.getMovieDetail(any()))
          .thenReturn(expected)

      // When
      val movieId = DataFactory.randomInt()
      val result = interactor.getMovieDetail(movieId)

      // Then
      assert(result == expected)
      val idCaptor = argumentCaptor<Int>()
      verify(repository).getMovieDetail(idCaptor.capture())
      assert(idCaptor.firstValue == movieId)
    }
  }

  @Test
  fun `get movie details error`() {
    runBlocking {
      // Given
      whenever(repository.getMovieDetail(any())).thenThrow(IllegalStateException())

      // When
      val movieId = DataFactory.randomInt()
      val result = kotlin.runCatching { interactor.getMovieDetail(movieId) }

      // Then
      assert(result.isFailure)
      assert(!result.isSuccess)
      assert(result.getOrNull() == null)
      assert(result.exceptionOrNull() is IllegalStateException)
      val idCaptor = argumentCaptor<Int>()
      verify(repository).getMovieDetail(idCaptor.capture())
      assert(idCaptor.firstValue == movieId)
    }
  }
}
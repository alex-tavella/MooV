package br.com.moov.domain.movie

import br.com.moov.domain.test.DomainDataFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieInteractorImplTest {

  private val repository: MovieRepository = mock()

  private lateinit var interactor: MoviesInteractor

  @Before
  fun setup() {
    interactor = MoviesInteractorImpl(repository)
  }

  @Test
  fun `get popular movies`() {
    runBlocking {
      // Given
      val expectedMovies = DomainDataFactory.newMovieList(5)
      whenever(repository.getPopularMovies(any())).thenReturn(expectedMovies)

      // When
      val list = interactor.getPopularMovies(1)

      // Then
      val pageCaptor = argumentCaptor<Int>()
      verify(repository).getPopularMovies(pageCaptor.capture())
      val result = pageCaptor.firstValue
      assert(result == 1)
      assert(list == expectedMovies)
    }
  }

  @Test
  fun `get popular movies paged`() {
    runBlocking {
      // Given
      val expectedMovies1 = DomainDataFactory.newMovieList(20)
      val page1 = 1
      whenever(repository.getPopularMovies(page1)).thenReturn(expectedMovies1)
      val expectedMovies2 = DomainDataFactory.newMovieList(10)
      val page2 = 2
      whenever(repository.getPopularMovies(page2)).thenReturn(expectedMovies2)


      // When
      val result1 = interactor.getPopularMovies(page1)
      val result2 = interactor.getPopularMovies(page2)

      // Then
      val pageCaptor = argumentCaptor<Int>()
      verify(repository, times(2)).getPopularMovies(pageCaptor.capture())
      val actualPage1 = pageCaptor.firstValue
      assert(actualPage1 == page1)
      assert(result1 == expectedMovies1)

      val actualPage2 = pageCaptor.secondValue
      assert(actualPage2 == page2)
      assert(result2 == expectedMovies2)
    }
  }

  @Test
  fun `get popular movies error`() {
    runBlocking {
      // Given
      whenever(repository.getPopularMovies(any())).thenThrow(IllegalStateException())

      val page = 1
      try {
        // When
        interactor.getPopularMovies(page)
        Fail.failBecauseExceptionWasNotThrown(IllegalStateException::class.java)
        // Then
      } catch (e: Exception) {
        assert(e is IllegalStateException)
        val pageCaptor = argumentCaptor<Int>()
        verify(repository).getPopularMovies(pageCaptor.capture())
        val actualPage = pageCaptor.firstValue
        assert(actualPage == page)
      }
    }
  }
}
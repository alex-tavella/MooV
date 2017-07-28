/*
 *     Copyright 2017 Alex Almeida Tavella
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package br.com.alex.moov.androidapp.home.list.movies

import br.com.alex.moov.BR
import br.com.alex.moov.androidapp.base.viewmodel.ViewModelTest
import br.com.alex.moov.androidapp.home.list.MoviesViewModel
import br.com.alex.moov.domain.entity.Movie
import br.com.alex.moov.domain.interactor.DiscoverMoviesInteractor
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times


class MoviesViewModelTest : ViewModelTest<MoviesViewModel>() {

  @Mock private lateinit var interactor: DiscoverMoviesInteractor

  private lateinit var firstPage: List<Movie>

  private lateinit var secondPage: List<Movie>

  private lateinit var thirdPage: List<Movie>

  override fun createViewModel(): MoviesViewModel = MoviesViewModel(
      SpiedMovieAdapter(appContext, mock()),
      interactor)

  @Before override fun setUp() {
    super.setUp()

    firstPage = listOf(Movie(title = "Shawshank Redemption"), Movie(title = "12 Angry Men"),
        Movie(title = "Django"), Movie(title = "Lord Of The Rings"))
    secondPage = listOf(Movie(title = "SpiderMan"), Movie(title = "Resident Evil"),
        Movie(title = "Kill Bill"), Movie(title = "The Arrival"))
    thirdPage = listOf(Movie(title = "La La Land"), Movie(title = "Goodfellas"),
        Movie(title = "City of God"), Movie(title = "Harry Potter"))

    `when`(interactor.execute(1)).thenReturn(Single.just(firstPage))
    `when`(interactor.execute(2)).thenReturn(Single.just(secondPage))
    `when`(interactor.execute(3)).thenReturn(Single.just(thirdPage))
  }

  @Test fun testViewModel() {
    // When
    viewModel.onStart()

    // Then
    val capturedMovies = viewModel.adapter.items
    assertNotNull(capturedMovies)
    assertEquals(firstPage.size, capturedMovies.size)
    firstPage.forEachIndexed { i, movie -> assertEquals(movie, capturedMovies[(i)]) }
  }

  @Test
  fun testRotation() {
    // Given
    viewModel.onStart()

    // Ween
    rotateDestroy()
    rotateCreate()

    // Then
    val capturedMovies = viewModel.adapter.items
    assertNotNull(capturedMovies)
    assertEquals(firstPage.size, capturedMovies.size)
    firstPage.forEachIndexed { i, movie -> assertEquals(movie, capturedMovies[(i)]) }
  }

  @Test fun testLoadMore() {
    // When
    viewModel.onStart()

    viewModel.onLoadMore()

    // Then
    verifyPropertyChanged(BR.loading, times(2))
    val capturedMovies = viewModel.adapter.items
    assertNotNull(capturedMovies)
    assertEquals(firstPage.size + secondPage.size, capturedMovies.size)
    firstPage.plus(secondPage).forEachIndexed { i, movie ->
      assertEquals(movie, capturedMovies[(i)])
    }
  }

  @Test fun testLoadMoreAndRotate() {
    // Given
    viewModel.onStart()
    viewModel.onLoadMore()

    // When
    rotateDestroy()
    rotateCreate()

    // Then
    val capturedMovies = viewModel.adapter.items
    assertNotNull(capturedMovies)
    assertEquals(firstPage.size + secondPage.size, capturedMovies.size)
    firstPage.plus(secondPage).forEachIndexed { i, movie ->
      assertEquals(movie, capturedMovies[(i)])
    }
  }

  @Test fun testLoadMoreTwice() {
    // When
    viewModel.onStart()

    viewModel.onLoadMore()
    viewModel.onLoadMore()

    // Then
    verifyPropertyChanged(BR.loading, times(4))
    val capturedMovies = viewModel.adapter.items
    assertNotNull(capturedMovies)
    assertEquals(firstPage.size + secondPage.size + thirdPage.size, capturedMovies.size)
    firstPage.plus(secondPage).plus(thirdPage).forEachIndexed { i, movie ->
      assertEquals(movie, capturedMovies[(i)])
    }
  }

  @Test fun testLoadMoreRotateAndLoadMore() {
    // Given
    viewModel.onStart()
    viewModel.onLoadMore()

    // When
    rotateDestroy()
    rotateCreate()
    viewModel.onLoadMore()

    // Then
    verifyPropertyChanged(BR.loading, times(2))
    val capturedMovies = viewModel.adapter.items
    assertNotNull(capturedMovies)
    assertEquals(firstPage.size + secondPage.size + thirdPage.size, capturedMovies.size)
    firstPage.plus(secondPage).plus(thirdPage).forEachIndexed { i, movie ->
      assertEquals(movie, capturedMovies[(i)])
    }
  }
}
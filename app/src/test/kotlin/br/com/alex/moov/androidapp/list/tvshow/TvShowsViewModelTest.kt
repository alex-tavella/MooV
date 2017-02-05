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

package br.com.alex.moov.androidapp.list.tvshow

import br.com.alex.moov.BR
import br.com.alex.moov.androidapp.base.viewmodel.ViewModelTest
import br.com.alex.moov.domain.entity.TvShow
import br.com.alex.moov.domain.interactor.DiscoverTvShowsInteractor
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class TvShowsViewModelTest : ViewModelTest<TvShowsViewModel>() {

  @Mock private lateinit var interactor: DiscoverTvShowsInteractor

  private lateinit var firstPage: List<TvShow>

  private lateinit var secondPage: List<TvShow>

  private lateinit var thirdPage: List<TvShow>

  override fun createViewModel(): TvShowsViewModel = TvShowsViewModel(
      SpiedTvShowAdapter(appContext), interactor)

  @Before override fun setUp() {
    super.setUp()

    firstPage = listOf(TvShow(name = "Game of Thrones"), TvShow(name = "Breaking Bad"),
        TvShow(name = "Arrow"), TvShow(name = "Modern Family"))
    secondPage = listOf(TvShow(name = "Scrubs"), TvShow(name = "Lucifer"),
        TvShow(name = "Preacher"), TvShow(name = "American Horror Story"))
    thirdPage = listOf(TvShow(name = "Westworld"), TvShow(name = "Better Call Saul"),
        TvShow(name = "The OA"), TvShow(name = "Vikings"))

    Mockito.`when`(interactor.execute(1)).thenReturn(Single.just(firstPage))
    Mockito.`when`(interactor.execute(2)).thenReturn(Single.just(secondPage))
    Mockito.`when`(interactor.execute(3)).thenReturn(Single.just(thirdPage))
  }

  @Test fun testViewModel() {
    // When
    viewModel.onStart()

    // Then
    val capturedTvShows = viewModel.adapter.items
    assertNotNull(capturedTvShows)
    assertEquals(firstPage.size, capturedTvShows.size)
    firstPage.forEachIndexed { i, movie -> assertEquals(movie, capturedTvShows.get(i)) }
  }

  @Test
  fun testRotation() {
    // Given
    viewModel.onStart()

    // Ween
    rotateDestroy()
    rotateCreate()

    // Then
    val capturedTvShows = viewModel.adapter.items
    assertNotNull(capturedTvShows)
    assertEquals(firstPage.size, capturedTvShows.size)
    firstPage.forEachIndexed { i, tvShow -> assertEquals(tvShow, capturedTvShows.get(i)) }
  }

  @Test fun testLoadMore() {
    // When
    viewModel.onStart()

    viewModel.onLoadMore()

    // Then
    verifyPropertyChanged(BR.loading, Mockito.times(2))
    val capturedMovies = viewModel.adapter.items
    assertNotNull(capturedMovies)
    assertEquals(firstPage.size + secondPage.size, capturedMovies.size)
    firstPage.plus(secondPage).forEachIndexed { i, movie ->
      assertEquals(movie, capturedMovies.get(i))
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
      assertEquals(movie, capturedMovies.get(i))
    }
  }

  @Test fun testLoadMoreTwice() {
    // When
    viewModel.onStart()

    viewModel.onLoadMore()
    viewModel.onLoadMore()

    // Then
    verifyPropertyChanged(BR.loading, Mockito.times(4))
    val capturedMovies = viewModel.adapter.items
    assertNotNull(capturedMovies)
    assertEquals(firstPage.size + secondPage.size + thirdPage.size, capturedMovies.size)
    firstPage.plus(secondPage).plus(thirdPage).forEachIndexed { i, movie ->
      assertEquals(movie, capturedMovies.get(i))
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
    verifyPropertyChanged(BR.loading, Mockito.times(2))
    val capturedMovies = viewModel.adapter.items
    assertNotNull(capturedMovies)
    assertEquals(firstPage.size + secondPage.size + thirdPage.size, capturedMovies.size)
    firstPage.plus(secondPage).plus(thirdPage).forEachIndexed { i, movie ->
      assertEquals(movie, capturedMovies.get(i))
    }
  }
}
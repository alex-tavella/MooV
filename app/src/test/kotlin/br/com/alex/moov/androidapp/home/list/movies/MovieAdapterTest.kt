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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.alex.moov.androidapp.BaseApplicationTest
import br.com.alex.moov.androidapp.base.viewmodel.list.RecyclerViewViewModelAdapter.ItemViewHolder
import br.com.alex.moov.androidapp.home.list.MovieItemViewModel
import br.com.alex.moov.databinding.GridItemMovieBinding
import br.com.alex.moov.domain.entity.Movie
import org.junit.Test
import org.mockito.Matchers.anyBoolean
import org.mockito.Matchers.anyInt
import org.mockito.Matchers.anyString
import org.mockito.Matchers.eq
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class MovieAdapterTest : BaseApplicationTest() {

  @Mock lateinit private var viewHolder: ItemViewHolder<Movie, MovieItemViewModel>

  @Mock lateinit private var binding: GridItemMovieBinding

  @Mock lateinit private var viewGroup: ViewGroup

  @Mock lateinit var view: View

  @Mock lateinit var layoutInflater: LayoutInflater

  @Mock lateinit private var movieItemViewModel: MovieItemViewModel

  lateinit private var movie: Movie

  lateinit private var items: List<Movie>

  lateinit private var adapter: SpiedMovieAdapter

  override fun setUp() {
    super.setUp()

    `when`(viewGroup.context).thenReturn(appContext)
    `when`(appContext.getSystemService(anyString())).thenReturn(layoutInflater)
    `when`(layoutInflater.inflate(anyInt(), eq(viewGroup), anyBoolean())).thenReturn(view)

    movie = Movie(title = "Lord of The Rings")

    items = listOf(movie, Movie(title = "Shawshank Redemption"),
        Movie(title = "12 Angry Men"), Movie(title = "Django"))

    adapter = SpiedMovieAdapter(appContext)
  }

  @Test
  fun testAddAll() {
    // Given
    assertFalse(adapter.notified)

    // When
    adapter.addAll(items)

    // Then
    assertTrue(adapter.notified)
  }

  @Test
  fun testGetItemCount() {
    // When
    adapter.addAll(items)

    // Then
    assertEquals(items.size, adapter.itemCount)
  }

  @Test
  fun testClear() {
    // Given
    adapter.addAll(items)
    assertEquals(items.size, adapter.itemCount)
    adapter.notified = false

    // When
    adapter.clear()

    // Then
    assertTrue(adapter.notified)
    assertEquals(0, adapter.itemCount)
  }

  @Test
  fun testOnCreateViewHolder() {
    // When
    val createdViewHolder = adapter.onCreateViewHolder(viewGroup, 0)

    // Then
    assertTrue(createdViewHolder is ItemViewHolder)
  }

  @Test
  fun testOnBindViewHolder() {
    // Given
    adapter.addAll(items)

    // When
    adapter.onBindViewHolder(viewHolder, 0)

    // Then
    verify(viewHolder).setItem(items[0])
  }

  @Test
  fun testViewHolderSetItem() {
    // Given
    val viewHolder = ItemViewHolder(view, binding, movieItemViewModel)

    // When
    viewHolder.setItem(movie)

    // Then
    verify(movieItemViewModel).setItem(movie)
  }
}
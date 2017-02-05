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

import android.view.View
import br.com.alex.moov.androidapp.base.viewmodel.ViewModelTest
import br.com.alex.moov.domain.entity.TvShow
import org.junit.Test
import org.mockito.Mock

class TvShowItemViewModelTest : ViewModelTest<TvShowItemViewModel>() {

  lateinit var tvShow: TvShow

  @Mock private lateinit var view: View

  override fun setUp() {
    super.setUp()

    tvShow = TvShow()
    tvShow.posterUrl = "poster url"
  }

  override fun createViewModel(): TvShowItemViewModel = TvShowItemViewModel()

  @Test fun testSetItem() {

    // When
    viewModel.setItem(tvShow)

    // Then
    verifyChanged()
    assertEquals("poster url", viewModel.getImageUrl())
  }

  @Test fun testPosterChanged() {

    // When
    viewModel.setItem(tvShow)

    // Then
    verifyChanged()
    assertEquals("poster url", viewModel.getImageUrl())

    // When
    tvShow.posterUrl = "new poster url"
    verifyChanged()

    // Then
    assertEquals("new poster url", viewModel.getImageUrl())
  }
}
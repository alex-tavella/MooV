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

package br.com.alex.moov.androidapp.list.movie

import android.databinding.Bindable
import android.view.View
import android.widget.Toast
import br.com.alex.moov.androidapp.base.viewmodel.list.ItemViewModel
import br.com.alex.moov.domain.entity.Movie


class MovieItemViewModel : ItemViewModel<Movie>() {

  private var movie: Movie? = null

  override fun setItem(item: Movie) {
    movie = item
    notifyChange()
  }

  @Bindable fun getImageUrl() = movie?.posterUrl

  fun onClick(view: View) {
    Toast.makeText(view.context, "${movie?.title}", Toast.LENGTH_SHORT).show()
  }
}
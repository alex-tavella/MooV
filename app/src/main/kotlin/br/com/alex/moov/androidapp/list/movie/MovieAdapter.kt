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

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.alex.moov.R
import br.com.alex.moov.androidapp.base.viewmodel.list.RecyclerViewViewModelAdapter
import br.com.alex.moov.databinding.GridItemMovieBinding
import br.com.alex.moov.domain.entity.Movie


class MovieAdapter(context: Context) : RecyclerViewViewModelAdapter<Movie, MovieItemViewModel>(
    context) {

  override fun onCreateViewHolder(parent: ViewGroup?,
      viewType: Int): ItemViewHolder<Movie, MovieItemViewModel> {
    val view = LayoutInflater.from(parent?.context).inflate(R.layout.grid_item_movie, parent,
        false)

    val binding = GridItemMovieBinding.bind(view)
    val viewModel = MovieItemViewModel()
    binding.viewModel = viewModel

    return MovieViewHolder(view, binding, viewModel)
  }

  internal class MovieViewHolder(itemView: View, val binding: GridItemMovieBinding,
      viewModel: MovieItemViewModel) : RecyclerViewViewModelAdapter.ItemViewHolder<Movie, MovieItemViewModel>(
      itemView, binding, viewModel)
}
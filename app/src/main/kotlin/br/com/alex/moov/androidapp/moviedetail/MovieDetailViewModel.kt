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

package br.com.alex.moov.androidapp.moviedetail

import android.databinding.Bindable
import br.com.alex.moov.androidapp.base.viewmodel.ViewModel
import br.com.alex.moov.domain.entity.Movie
import br.com.alex.moov.domain.interactor.GetMovieInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieDetailViewModel(val movieInteractor: GetMovieInteractor) : ViewModel() {

  var movieId: Int = -1

  var movie: Movie? = null

  override fun onStart() {
    super.onStart()
    disposableContainer.add(
        movieInteractor.execute(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
              it ->
              movie = it
              notifyChange()
            })
  }

  @Bindable fun getMovieTitle() = movie?.title

  @Bindable fun getMoviePoster() = movie?.posterUrl

  @Bindable fun getMovieOverview() = movie?.overview
}
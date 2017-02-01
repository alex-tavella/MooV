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
import android.os.Parcel
import android.os.Parcelable
import br.com.alex.moov.androidapp.base.viewmodel.ViewModel
import br.com.alex.moov.androidapp.base.viewmodel.list.RecyclerViewViewModel
import br.com.alex.moov.domain.entity.Movie
import br.com.alex.moov.domain.interactor.DiscoverMoviesInteractor
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber

class MoviesViewModel(val context: Context, val adapter: MovieAdapter,
    val discoverMoviesInteractor: DiscoverMoviesInteractor,
    savedState: State?) : RecyclerViewViewModel(savedState) {

  override fun getRecyclerViewViewModelAdapter() = adapter

  override fun getInstanceState(): ViewModel.State
      = MoviesViewModelState(this)

  private var subscription: Subscription? = null

  override fun onStart() {
    super.onStart()
    subscription = discoverMoviesInteractor.execute()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
          Timber.i(it.map { it.title }.toString())
          adapter.setItems(it.sortedBy { it.title })
        }, { Timber.w(it.toString()) })
  }

  override fun onStop() {
    super.onStop()
    subscription?.unsubscribe()
  }

  private class MoviesViewModelState : ViewModel.State {

    private val movies: MutableList<Movie>

    constructor(viewModel: MoviesViewModel) : super(viewModel) {
      movies = viewModel.adapter.items
    }

    constructor(`in`: Parcel) : super(`in`) {
      movies = `in`.createTypedArrayList(Movie.CREATOR)
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
      super.writeToParcel(dest, flags)
      dest!!.writeTypedList(movies)
    }

    companion object {

      var CREATOR: Parcelable.Creator<MoviesViewModelState> = object : Parcelable.Creator<MoviesViewModelState> {
        override fun createFromParcel(source: Parcel): MoviesViewModelState {
          return MoviesViewModelState(source)
        }

        override fun newArray(size: Int): Array<MoviesViewModelState?> {
          return arrayOfNulls(size)
        }
      }
    }
  }
}
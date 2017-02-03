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
import android.databinding.Bindable
import android.os.Parcel
import android.os.Parcelable
import br.com.alex.moov.BR
import br.com.alex.moov.androidapp.base.viewmodel.ViewModel
import br.com.alex.moov.androidapp.base.viewmodel.list.RecyclerViewViewModel
import br.com.alex.moov.androidapp.list.OnLoadMoreListener
import br.com.alex.moov.domain.entity.Movie
import br.com.alex.moov.domain.interactor.DiscoverMoviesInteractor
import rx.Single
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import timber.log.Timber

class MoviesViewModel(val context: Context, val adapter: MovieAdapter,
    val discoverMoviesInteractor: DiscoverMoviesInteractor,
    savedState: State?) : RecyclerViewViewModel(savedState), OnLoadMoreListener {

  private var page: Int = 1

  private var isLoading: Boolean = false
    set(value) {
      field = value
      notifyPropertyChanged(BR.loading)
    }

  private val compositeSubscription: CompositeSubscription = CompositeSubscription()

  override fun getRecyclerViewViewModelAdapter() = adapter

  override fun getInstanceState(): ViewModel.State
      = MoviesViewModelState(this)

  override fun onStart() {
    super.onStart()
    compositeSubscription.add(discoverMoviesInteractor.execute(page)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
          Timber.i(it.map { it.title }.toString())
          adapter.addAll(it)
        }, { Timber.w(it.toString()) }))
  }

  override fun onStop() {
    super.onStop()
    compositeSubscription.clear()
  }

  override fun onLoadMore() {
    if (!isLoading) {
      compositeSubscription.add(Single.just("Stub")
          .doOnSuccess { isLoading = true }
          .flatMap { discoverMoviesInteractor.execute(page + 1) }
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe({
            Timber.i(it.map { it.title }.toString())
            adapter.addAll(it)
            page++
            isLoading = false
          }, {
            Timber.w(it.toString())
            isLoading = false
          }))
    }
  }

  @Bindable fun isLoading() = isLoading

  private class MoviesViewModelState : ViewModel.State {

    private val movies: MutableList<Movie>

    private val currentPage: Int

    constructor(viewModel: MoviesViewModel) : super(viewModel) {
      movies = viewModel.adapter.items
      currentPage = viewModel.page
    }

    constructor(`in`: Parcel) : super(`in`) {
      movies = `in`.createTypedArrayList(Movie.CREATOR)
      currentPage = `in`.readInt()
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
      super.writeToParcel(dest, flags)
      dest!!.writeTypedList(movies)
      dest.writeInt(currentPage)
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
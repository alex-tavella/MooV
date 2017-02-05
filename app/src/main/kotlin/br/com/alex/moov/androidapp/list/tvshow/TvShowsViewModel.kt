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

import android.databinding.Bindable
import android.os.Parcel
import android.os.Parcelable
import br.com.alex.moov.BR
import br.com.alex.moov.androidapp.base.viewmodel.ViewModel
import br.com.alex.moov.androidapp.base.viewmodel.list.RecyclerViewViewModel
import br.com.alex.moov.androidapp.list.OnLoadMoreListener
import br.com.alex.moov.domain.entity.TvShow
import br.com.alex.moov.domain.interactor.DiscoverTvShowsInteractor
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class TvShowsViewModel(val adapter: TvShowAdapter,
    private val discoverTvShowsInteractor: DiscoverTvShowsInteractor) : RecyclerViewViewModel(), OnLoadMoreListener {

  private var disposable: Disposable? = null

  private var page: Int = 1

  private var isLoading: Boolean = false
    set(value) {
      field = value
      notifyPropertyChanged(BR.loading)
    }

  private var restoredFromSavedState = false

  override fun getRecyclerViewViewModelAdapter() = adapter

  override fun getInstanceState(): ViewModel.State
      = TvShowsViewModelState(this)

  override fun onRestoreState(savedInstanceState: State?) {
    super.onRestoreState(savedInstanceState)

    if (savedInstanceState is TvShowsViewModelState) {
      adapter.clear()
      adapter.addAll(savedInstanceState.tvShows)
      page = savedInstanceState.currentPage
      notifyChange()
      Timber.i("Restored view model!")
      restoredFromSavedState = true
    }
  }

  override fun onStart() {
    super.onStart()
    if (!restoredFromSavedState) {
      disposable = discoverTvShowsInteractor.execute(page)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe({
            Timber.i(it.map { it.name }.toString())
            adapter.addAll(it)
          }, { Timber.w(it.toString()) })
    } else {
      restoredFromSavedState = false
    }
  }

  override fun onStop() {
    super.onStop()
    disposable?.dispose()
  }

  override fun onLoadMore() {
    if (!isLoading) {
      disposable = Single.just("Stub")
          .doOnSuccess { isLoading = true }
          .flatMap { discoverTvShowsInteractor.execute(page + 1) }
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe({
            Timber.i(it.map { it.name }.toString())
            adapter.addAll(it)
            page++
            isLoading = false
          }, {
            Timber.w(it.toString())
            isLoading = false
          })
    }
  }

  @Bindable fun isLoading() = isLoading

  private class TvShowsViewModelState : ViewModel.State {

    companion object {

      var CREATOR: Parcelable.Creator<TvShowsViewModelState> = object : Parcelable.Creator<TvShowsViewModelState> {
        override fun createFromParcel(source: Parcel): TvShowsViewModelState {
          return TvShowsViewModelState(source)
        }

        override fun newArray(size: Int): Array<TvShowsViewModelState?> {
          return arrayOfNulls(size)
        }
      }
    }

    val tvShows: MutableList<TvShow>

    val currentPage: Int

    constructor(viewModel: TvShowsViewModel) {
      tvShows = viewModel.adapter.items
      currentPage = viewModel.page
    }

    constructor(`in`: Parcel) {
      tvShows = `in`.createTypedArrayList(TvShow.CREATOR)
      currentPage = `in`.readInt()
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
      dest!!.writeTypedList(tvShows)
      dest.writeInt(currentPage)
    }
  }
}
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

import android.content.Context
import android.databinding.Bindable
import android.os.Parcel
import android.os.Parcelable
import br.com.alex.moov.BR
import br.com.alex.moov.androidapp.base.viewmodel.ViewModel
import br.com.alex.moov.androidapp.base.viewmodel.list.RecyclerViewViewModel
import br.com.alex.moov.androidapp.list.OnLoadMoreListener
import br.com.alex.moov.domain.entity.TvShow
import br.com.alex.moov.domain.interactor.DiscoverTvShowsInteractor
import rx.Single
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import timber.log.Timber

class TvShowsViewModel(val context: Context, val adapter: TvShowAdapter, savedState: State?,
    val discoverTvShowsInteractor: DiscoverTvShowsInteractor) : RecyclerViewViewModel(
    savedState), OnLoadMoreListener {
  private val compositeSubscription: CompositeSubscription = CompositeSubscription()

  private var page: Int = 1

  private var isLoading: Boolean = false
    set(value) {
      field = value
      notifyPropertyChanged(BR.loading)
    }

  override fun getRecyclerViewViewModelAdapter() = adapter

  override fun getInstanceState(): ViewModel.State
      = TvShowsViewModelState(this)

  override fun onStart() {
    super.onStart()
    compositeSubscription.add(discoverTvShowsInteractor.execute(page)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
          Timber.i(it.map { it.name }.toString())
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
          }))
    }
  }

  @Bindable fun isLoading() = isLoading

  private class TvShowsViewModelState : ViewModel.State {

    private val tvShows: MutableList<TvShow>

    private val currentPage: Int

    constructor(viewModel: TvShowsViewModel) : super(viewModel) {
      tvShows = viewModel.adapter.items
      currentPage = viewModel.page
    }

    constructor(`in`: Parcel) : super(`in`) {
      tvShows = `in`.createTypedArrayList(TvShow.CREATOR)
      currentPage = `in`.readInt()
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
      super.writeToParcel(dest, flags)
      dest!!.writeTypedList(tvShows)
      dest.writeInt(currentPage)
    }

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
  }
}
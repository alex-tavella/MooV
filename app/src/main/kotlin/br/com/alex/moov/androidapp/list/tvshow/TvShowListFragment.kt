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

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.OnScrollListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.alex.moov.R
import br.com.alex.moov.androidapp.ApplicationComponent
import br.com.alex.moov.androidapp.base.BaseFragment
import br.com.alex.moov.androidapp.base.di.HasComponent
import br.com.alex.moov.androidapp.base.viewmodel.ViewModel
import br.com.alex.moov.androidapp.base.viewmodel.ViewModel.State
import br.com.alex.moov.androidapp.home.HomeComponent
import br.com.alex.moov.androidapp.list.MarginDecoration
import br.com.alex.moov.androidapp.list.OnLoadMoreListener
import br.com.alex.moov.androidapp.logger.EventLogger
import br.com.alex.moov.databinding.FragmentTvShowsBinding
import br.com.alex.moov.domain.interactor.DiscoverTvShowsInteractor
import javax.inject.Inject

class TvShowListFragment : BaseFragment(), HasComponent<TvShowsComponent> {
  private var tvShowsViewModel: TvShowsViewModel? = null

  private lateinit var tvShowsComponent: TvShowsComponent

  @Inject lateinit var tvShowAdapter: TvShowAdapter

  @Inject lateinit var discoverTvShowsInteractor: DiscoverTvShowsInteractor

  @Inject lateinit var eventLogger: EventLogger

  override fun injectDependencies(applicationComponent: ApplicationComponent) {
    val activity = getActivity()
    if (activity is HasComponent<*>) {
      val component = activity.getComponent()
      if (component is HomeComponent) {
        tvShowsComponent = DaggerTvShowsComponent.builder()
            .homeComponent(component)
            .tvShowsModule(TvShowsModule())
            .build()
        tvShowsComponent.inject(this)
      }
    }
  }

  override fun getComponent() = tvShowsComponent

  override fun createAndBindViewModel(root: View, savedViewModelState: State?): ViewModel {
    tvShowsViewModel = TvShowsViewModel(context, tvShowAdapter, savedViewModelState,
        discoverTvShowsInteractor)
    val binding = FragmentTvShowsBinding.bind(root)
    binding.setViewModel(tvShowsViewModel)
    binding.recyclerView.setHasFixedSize(true)
    binding.recyclerView.addItemDecoration(MarginDecoration(context))
    binding.recyclerView.addOnScrollListener(object: OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = binding.recyclerView.layoutManager as GridLayoutManager
        val totalItemCount = layoutManager.itemCount
        val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
        if (totalItemCount <= (lastVisibleItem + 3)) {
          if (tvShowsViewModel is OnLoadMoreListener) {
            (tvShowsViewModel as OnLoadMoreListener).onLoadMore()
          }
        }
      }
    })
    return tvShowsViewModel!!
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?) = inflater!!.inflate(R.layout.fragment_tv_shows, container,
      false)

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    eventLogger.logContentView("Tv Shows Screen")
  }
}
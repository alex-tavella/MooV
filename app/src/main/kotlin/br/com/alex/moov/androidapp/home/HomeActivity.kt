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

package br.com.alex.moov.androidapp.home

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.OnScrollListener
import android.view.Menu
import android.view.MenuItem
import br.com.alex.moov.R
import br.com.alex.moov.androidapp.base.BaseActivity
import br.com.alex.moov.androidapp.base.di.ApplicationComponent
import br.com.alex.moov.androidapp.base.di.HasComponent
import br.com.alex.moov.androidapp.base.di.home.HomeComponent
import br.com.alex.moov.androidapp.base.di.home.HomeModule
import br.com.alex.moov.androidapp.base.viewmodel.ViewModel
import br.com.alex.moov.androidapp.email.EmailSender
import br.com.alex.moov.androidapp.home.list.MarginDecoration
import br.com.alex.moov.androidapp.home.list.MovieAdapter
import br.com.alex.moov.androidapp.home.list.MoviesViewModel
import br.com.alex.moov.androidapp.home.list.OnLoadMoreListener
import br.com.alex.moov.androidapp.logger.EventLogger
import br.com.alex.moov.databinding.ActivityHomeBinding
import br.com.alex.moov.domain.interactor.DiscoverMoviesInteractor
import javax.inject.Inject

class HomeActivity : BaseActivity(), HasComponent<HomeComponent> {

  lateinit var homeComponent: HomeComponent

  @Inject lateinit var screenSwitcher: HomeScreenSwitcher

  @Inject lateinit var movieAdapter: MovieAdapter

  @Inject lateinit var discoverMoviesInteractor: DiscoverMoviesInteractor

  @Inject lateinit var moviesViewModel: MoviesViewModel

  @Inject lateinit var emailSender: EmailSender

  @Inject lateinit var eventLogger: EventLogger

  override fun getComponent() = homeComponent

  val mBinding: ActivityHomeBinding by lazy {
    DataBindingUtil.setContentView<ActivityHomeBinding>(this,
        R.layout.activity_home)
  }

  override fun injectDependencies(applicationComponent: ApplicationComponent) {
    homeComponent = applicationComponent.plus(HomeModule(this))
    homeComponent.inject(this)
  }

  override fun createViewModel(): ViewModel {
    return moviesViewModel
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)

    mBinding.viewModel = moviesViewModel
    mBinding.recyclerView.setHasFixedSize(true)
    mBinding.recyclerView.addItemDecoration(MarginDecoration(this))
    mBinding.recyclerView.addOnScrollListener(object : OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = mBinding.recyclerView.layoutManager as GridLayoutManager
        val totalItemCount = layoutManager.itemCount
        val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
        if (totalItemCount <= (lastVisibleItem + 3)) {
          if (moviesViewModel is OnLoadMoreListener) {
            (moviesViewModel as OnLoadMoreListener).onLoadMore()
          }
        }
      }
    })
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.activity_home_drawer, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    item?.let {
      when (item.itemId) {
        R.id.nav_about -> {
          screenSwitcher.switchToAboutScreen()
        }
        R.id.nav_feedback -> {
          emailSender.sendFeedbackEmail()
        }
      }
    }
    return super.onOptionsItemSelected(item)
  }
}

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
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import br.com.alex.moov.R
import br.com.alex.moov.androidapp.ApplicationComponent
import br.com.alex.moov.androidapp.about.AboutFragment
import br.com.alex.moov.androidapp.base.BaseActivity
import br.com.alex.moov.androidapp.base.di.HasComponent
import br.com.alex.moov.androidapp.base.viewmodel.ViewModel
import br.com.alex.moov.androidapp.email.EmailSender
import br.com.alex.moov.androidapp.list.movie.MovieListFragment
import br.com.alex.moov.androidapp.list.tvshow.TvShowListFragment
import br.com.alex.moov.androidapp.logger.EventLogger
import br.com.alex.moov.databinding.ActivityHomeBinding
import javax.inject.Inject

class HomeActivity : BaseActivity(), OnNavigationItemSelectedListener, HasComponent<HomeComponent> {

  companion object {
    val TAG_MOVIES = "Movies"
    val TAG_TV_SHOWS = "TvShows"
    val TAG_ABOUT = "About"
  }

  lateinit var homeComponent: HomeComponent

  @Inject lateinit var screenSwitcher: HomeScreenSwitcher

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

  override fun createViewModel(): ViewModel? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)

    val drawerLayout = mBinding.drawerLayout
    val toolbar = mBinding.toolbar

    setSupportActionBar(toolbar)

    val toggle = ActionBarDrawerToggle(
        this, drawerLayout, toolbar, R.string.navigation_drawer_open,
        R.string.navigation_drawer_close)
    drawerLayout.addDrawerListener(toggle)
    toggle.syncState()

    mBinding.navView.setNavigationItemSelectedListener(this)

    if (savedInstanceState == null) {
      screenSwitcher.switchScreen(MovieListFragment::class.java, TAG_MOVIES)
      mBinding.navView.setCheckedItem(R.id.nav_movies)
    }
  }

  override fun onBackPressed() {
    val drawer = mBinding.drawerLayout
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START)
    } else {
      super.onBackPressed()
    }
  }

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.nav_movies -> {
        screenSwitcher.switchScreen(MovieListFragment::class.java, TAG_MOVIES)
      }
      R.id.nav_tv_shows -> {
        screenSwitcher.switchScreen(TvShowListFragment::class.java, TAG_TV_SHOWS)
      }
      R.id.nav_about -> {
        screenSwitcher.switchScreen(AboutFragment::class.java, TAG_ABOUT)
      }
      R.id.nav_feedback -> {
        emailSender.sendFeedbackEmail()
      }
      else -> {
      }
    }

    eventLogger.logHomeNavigationDrawerEvent(item)

    mBinding.drawerLayout.closeDrawer(GravityCompat.START)
    return true
  }
}

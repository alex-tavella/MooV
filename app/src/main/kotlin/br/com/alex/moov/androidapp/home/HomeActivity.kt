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
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import br.com.alex.moov.R
import br.com.alex.moov.androidapp.MooVApplication
import br.com.alex.moov.databinding.ActivityHomeBinding
import br.com.alex.moov.domain.service.MovieService
import br.com.alex.moov.domain.service.TvShowService
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.CustomEvent
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), OnNavigationItemSelectedListener {

  val mBinding: ActivityHomeBinding by lazy {
    DataBindingUtil.setContentView<ActivityHomeBinding>(this,
        R.layout.activity_home)
  }

  @Inject lateinit var moviesService: MovieService

  @Inject lateinit var tvShowsService: TvShowService

  var subscription: Subscription? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    (applicationContext as MooVApplication).getAppComponent().inject(this)

    val drawerLayout = mBinding.drawerLayout
    val toolbar = mBinding.appBar.toolbar

    setSupportActionBar(toolbar)

    val toggle = ActionBarDrawerToggle(
        this, drawerLayout, toolbar, R.string.navigation_drawer_open,
        R.string.navigation_drawer_close)
    drawerLayout.addDrawerListener(toggle)
    toggle.syncState()

    mBinding.navView.setNavigationItemSelectedListener(this)
  }

  override fun onBackPressed() {
    val drawer = mBinding.drawerLayout
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START)
    } else {
      super.onBackPressed()
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    subscription?.unsubscribe()
  }

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.nav_camera -> {
        subscription = moviesService.discoverMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ Timber.d(it.toString()) }, { Timber.w(it.toString()) })
      }
      R.id.nav_gallery -> {
        subscription = tvShowsService.discoverTvShows()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ Timber.d(it.toString()) }, { Timber.w(it.toString()) })
      }
      R.id.nav_slideshow -> {
      }
      R.id.nav_manage -> {
      }
      R.id.nav_share -> {
      }
      R.id.nav_send -> {
      }
      else -> {
      }
    }

    Answers.getInstance().logCustom(CustomEvent("Navigation item selected")
        .putCustomAttribute("Context", "Home drawer")
        .putCustomAttribute("item", item.toString()))

    mBinding.drawerLayout.closeDrawer(GravityCompat.START)
    return true
  }
}

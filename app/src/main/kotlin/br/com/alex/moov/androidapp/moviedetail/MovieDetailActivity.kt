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

import android.databinding.DataBindingUtil
import android.os.Bundle
import br.com.alex.moov.R
import br.com.alex.moov.androidapp.base.BaseActivity
import br.com.alex.moov.androidapp.base.di.ApplicationComponent
import br.com.alex.moov.androidapp.base.di.moviedetail.MovieDetailModule
import br.com.alex.moov.androidapp.logger.EventLogger
import br.com.alex.moov.databinding.ActivityMovieDetailBinding
import kotlinx.android.synthetic.main.activity_movie_detail.toolbar
import javax.inject.Inject

class MovieDetailActivity : BaseActivity() {
  companion object {
    val EXTRA_MOVIE_ID = "movie_id"
  }

  @Inject lateinit var mViewModel: MovieDetailViewModel

  @Inject lateinit var eventLogger: EventLogger

  val mBinding: ActivityMovieDetailBinding by lazy {
    DataBindingUtil.setContentView<ActivityMovieDetailBinding>(this,
        R.layout.activity_movie_detail)
  }

  override fun injectDependencies(applicationComponent: ApplicationComponent) {
    applicationComponent.plus(MovieDetailModule(this)).inject(this)
  }

  override fun createViewModel() = mViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    mBinding.viewModel = mViewModel

    val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, -1)
    mViewModel.movieId = movieId

    setSupportActionBar(toolbar)
    setUpTopBar()

    eventLogger.logContentView("Movie Detail Screen")
  }

  private fun setUpTopBar() {
    toolbar.title = null
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }
}

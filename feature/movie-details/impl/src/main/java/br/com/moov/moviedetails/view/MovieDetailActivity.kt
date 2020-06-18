/*
 * Copyright 2020 Alex Almeida Tavella
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.moov.moviedetails.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import br.com.core.android.BaseActivity
import br.com.core.android.logd
import br.com.core.android.views.DialogFactory
import br.com.moov.moviedetails.R
import br.com.moov.moviedetails.di.DaggerMovieDetailComponent
import br.com.moov.moviedetails.di.MovieDetailsDependencies
import br.com.moov.moviedetails.viewmodel.MovieDetailUiEvent
import br.com.moov.moviedetails.viewmodel.MovieDetailUiModel
import br.com.moov.moviedetails.viewmodel.MovieDetailViewModel
import com.bumptech.glide.Glide
import dagger.hilt.EntryPoints
import javax.inject.Inject

class MovieDetailActivity : BaseActivity() {

    @Inject
    internal lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<MovieDetailViewModel> { viewModelProviderFactory }

    private var uiModel: MovieDetailUiModel? = null

    private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }
    private val posterView by lazy { findViewById<ImageView>(R.id.iv_movie_poster) }
    private val backdropView by lazy { findViewById<ImageView>(R.id.iv_movie_backdrop) }
    private val ratingView by lazy { findViewById<TextView>(R.id.tv_movie_rating) }
    private val cardView by lazy { findViewById<View>(R.id.cv_movie_title_background) }
    private val titleView by lazy { findViewById<TextView>(R.id.tv_movie_title) }
    private val releaseDateView by lazy { findViewById<TextView>(R.id.tv_release_date) }
    private val genreView by lazy { findViewById<TextView>(R.id.tv_movie_genres) }
    private val summaryView by lazy { findViewById<TextView>(R.id.tv_movie_summary) }
    private val loadingView by lazy { findViewById<ProgressBar>(R.id.progressBar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerMovieDetailComponent.factory()
            .create(EntryPoints.get(applicationContext, MovieDetailsDependencies::class.java))
            .inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        setSupportActionBar(toolbar)
        setUpTopBar()
    }

    override fun onStart() {
        super.onStart()
        viewModel.observe(this, ::renderUiModel)

        intent.getIntExtra(EXTRA_MOVIE_ID, -1)
            .takeUnless { it == -1 }
            ?.let { viewModel.uiEvent(MovieDetailUiEvent.EnterScreen(it)) }
            ?: presentError(getString(R.string.error_message_movie_detail_load))
    }

    private fun setUpTopBar() {
        toolbar.title = null
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = null
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_movie_detail, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val result = super.onPrepareOptionsMenu(menu)
        menu?.findItem(R.id.menu_toggle_favorite)?.apply {
            val isFavorite = uiModel?.movie?.isBookmarked ?: false
            setIcon(
                if (isFavorite)
                    br.com.moov.bookmark.movie.R.drawable.ic_favorite
                else
                    br.com.moov.bookmark.movie.R.drawable.ic_favorite_border
            )
        }
        return result
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                finish()
            }
            R.id.menu_toggle_favorite -> uiModel?.movie?.let {
                viewModel.uiEvent(MovieDetailUiEvent.MovieFavoritedUiEvent(it, !it.isBookmarked))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun renderUiModel(movieDetailUiModel: MovieDetailUiModel) {
        logd { "Rendering $movieDetailUiModel" }
        uiModel = movieDetailUiModel

        // Visibility
        loadingView.visibility = if (movieDetailUiModel.loading) View.VISIBLE else View.GONE
        val uiVisibility = if (movieDetailUiModel.loading) View.GONE else View.VISIBLE
        ratingView.visibility = uiVisibility
        cardView.visibility = uiVisibility

        // Data
        movieDetailUiModel.movie?.also { movieDetail ->
            toolbar.title = movieDetail.title
            supportActionBar?.title = movieDetail.title
            val glide = Glide.with(this)
            movieDetail.posterUrl?.let { glide.load(it).into(posterView) }
            movieDetail.backdropUrl?.let { glide.load(it).into(backdropView) }
            ratingView.text = movieDetail.voteAverage.toString()
            titleView.text = movieDetail.title
            releaseDateView.text = movieDetail.releaseDate
            movieDetail.genres.reduce { acc, s -> "$acc, $s" }.let { genreView.text = it }
            summaryView.text = movieDetail.overview
        }

        // Error
        movieDetailUiModel.error
            ?.let {
                presentError(getString(R.string.error_message_movie_detail_param, it.message))
            }
        invalidateOptionsMenu()
    }

    private fun presentError(errorMessage: String) {
        DialogFactory.createErrorDialog(this, errorMessage) { _, _ -> finish() }
            .show()
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
    }

    companion object {

        private const val EXTRA_MOVIE_ID = "movie_id"

        private const val SHARED_ELEMENT_NAME_POSTER = "poster"

        fun start(activity: Activity, movieId: Int, sharedView: View?) {
            val options = sharedView?.let {
                ActivityOptionsCompat
                    .makeSceneTransitionAnimation(
                        activity, it,
                        SHARED_ELEMENT_NAME_POSTER
                    )
            }
            activity.startActivity(
                Intent(activity, MovieDetailActivity::class.java)
                    .putExtra(EXTRA_MOVIE_ID, movieId), options?.toBundle()
            )
        }
    }
}

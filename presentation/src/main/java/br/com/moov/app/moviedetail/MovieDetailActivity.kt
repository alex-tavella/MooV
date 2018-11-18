package br.com.moov.app.moviedetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityOptionsCompat
import br.com.moov.app.R
import br.com.moov.app.core.BaseActivity
import br.com.moov.app.util.DialogFactory
import br.com.moov.app.util.logd
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailActivity : BaseActivity() {

  private val viewModel by viewModel<MovieDetailViewModel>()

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
        ?.let { viewModel.uiEvent(EnterScreen(it)) }
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
      setIcon(if (isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_border)
    }
    return result
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.menu_toggle_favorite -> uiModel?.movie?.let {
        viewModel.uiEvent(MovieFavoritedUiEvent(it, !it.isBookmarked))
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

    fun start(activity: Activity, movieId: Int, sharedView: View) {
      val options = ActivityOptionsCompat
          .makeSceneTransitionAnimation(activity, sharedView, SHARED_ELEMENT_NAME_POSTER)
      activity.startActivity(Intent(activity, MovieDetailActivity::class.java)
          .putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, movieId), options.toBundle())
    }
  }
}
package br.com.moov.app.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import br.com.moov.app.R
import br.com.moov.app.core.BaseFragment
import br.com.moov.app.moviedetail.MovieDetailActivity
import br.com.moov.app.util.DialogFactory
import br.com.moov.app.util.logd
import br.com.moov.app.util.onEndReached
import br.com.moov.domain.movie.Movie
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : BaseFragment() {

  private val viewModel by viewModel<MoviesViewModel>()

  private val loadingProgressBar: ProgressBar? by lazy {
    view?.findViewById<ProgressBar>(R.id.progressBar)
  }

  private val recyclerView by lazy { view?.findViewById<RecyclerView>(R.id.rv_movies) }

  private val adapter by lazy {
    context?.let {
      MovieAdapter(it, this::onMovieClick, this::onMovieFavorite)
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_movies, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    recyclerView?.apply {
      setHasFixedSize(true)
      context?.let { addItemDecoration(MarginDecoration(it)) }
      adapter = this@MoviesFragment.adapter
      onEndReached(this@MoviesFragment) {
        logd { "Emitting finished scroll ui event" }
        viewModel.uiEvent(FinishedScrollingUiEvent)
      }
    }
  }

  override fun onStart() {
    super.onStart()
    viewModel.observe(this, ::renderUiModel)
    logd { "Emitting enter screen ui event" }
    viewModel.uiEvent(EnterScreenUiEvent)
  }

  private fun renderUiModel(uiModel: MoviesUiModel) {
    logd { "Rendering model $uiModel" }
    loadingProgressBar?.visibility = if (uiModel.loading) View.VISIBLE else View.GONE
    adapter?.setItems(uiModel.movies)

    uiModel.error
        ?.let {
          activity?.let { nnActivity ->
            DialogFactory.createErrorDialog(nnActivity,
                "Error when loading movies: ${it.message}") { _, _ -> }
                .show()
          }
        }
  }

  private fun onMovieClick(view: View, movieId: Int?) {
    activity?.let { nnActivity ->
      movieId?.let { MovieDetailActivity.start(nnActivity, it, view) }
    }
  }

  private fun onMovieFavorite(favorited: Boolean, movie: Movie) {
    viewModel.uiEvent(MovieFavoritedUiEvent(movie, favorited))
  }
}
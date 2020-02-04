package br.com.moov.app.movies

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import br.com.moov.app.R
import br.com.moov.app.core.BaseFragment
import br.com.moov.app.core.appComponent
import br.com.moov.app.core.viewModel
import br.com.moov.app.moviedetail.MovieDetailActivity
import br.com.moov.app.movies.MoviesUiEvent.EnterScreenUiEvent
import br.com.moov.app.movies.MoviesUiEvent.FinishedScrollingUiEvent
import br.com.moov.app.movies.MoviesUiEvent.MovieFavoritedUiEvent
import br.com.moov.app.util.DialogFactory
import br.com.moov.app.util.logd
import br.com.moov.app.util.onEndReached
import br.com.moov.domain.movie.Movie
import javax.inject.Inject

class MoviesFragment : BaseFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private val viewModel by viewModel<MoviesViewModel>(viewModelProviderFactory)

    private val loadingProgressBar: ProgressBar? by lazy {
        view?.findViewById<ProgressBar>(R.id.progressBar)
    }

    private val recyclerView by lazy { view?.findViewById<RecyclerView>(R.id.rv_movies) }

    private val adapter by lazy { MovieAdapter(this::onMovieClick, this::onMovieFavorite) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        adapter.setItems(uiModel.movies)

        uiModel.error
            ?.let {
                activity?.let { nnActivity ->
                    DialogFactory.createErrorDialog(
                        nnActivity,
                        getString(R.string.error_message_movies_param, it.message)
                    ) { _, _ -> }
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

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
package br.com.moov.movies.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import br.com.core.android.BaseFragment
import br.com.core.android.logd
import br.com.core.android.views.DialogFactory
import br.com.core.android.views.MarginDecoration
import br.com.core.android.views.onEndReached
import br.com.moov.dibridge.coreComponent
import br.com.moov.moviedetails.navigation.MovieDetailsNavigator
import br.com.moov.movies.R
import br.com.moov.movies.di.DaggerMoviesComponent
import br.com.moov.movies.domain.Movie
import br.com.moov.movies.viewmodel.MoviesUiEvent
import br.com.moov.movies.viewmodel.MoviesUiModel
import br.com.moov.movies.viewmodel.MoviesViewModel
import javax.inject.Inject

internal class MoviesFragment : BaseFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    @Inject
    lateinit var movieDetailsNavigator: MovieDetailsNavigator

    private val viewModel by viewModels<MoviesViewModel> { viewModelProviderFactory }

    private val loadingProgressBar: ProgressBar? by lazy {
        view?.findViewById<ProgressBar>(R.id.progressBar)
    }

    private val recyclerView by lazy { view?.findViewById<RecyclerView>(R.id.rv_movies) }

    private val adapter by lazy { MovieAdapter(this::onMovieClick, this::onMovieFavorite) }

    override fun onAttach(context: Context) {
        DaggerMoviesComponent.factory()
            .create(coreComponent())
            .inject(this)
        super.onAttach(context)
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
                viewModel.uiEvent(MoviesUiEvent.FinishedScrollingUiEvent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.observe(this, ::renderUiModel)
        logd { "Emitting enter screen ui event" }
        viewModel.uiEvent(MoviesUiEvent.EnterScreenUiEvent)
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

    private fun onMovieClick(movieId: Int?, itemView: View) {
        activity?.let { nnActivity ->
            movieId?.let { movieDetailsNavigator.openMovieDetailsScreen(nnActivity, it, itemView) }
        }
    }

    private fun onMovieFavorite(favorited: Boolean, movie: Movie) {
        viewModel.uiEvent(MoviesUiEvent.MovieFavoritedUiEvent(movie, favorited))
    }
}

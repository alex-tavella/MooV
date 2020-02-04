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
package br.com.moov.app.movies

import androidx.annotation.VisibleForTesting
import br.com.moov.app.core.BaseViewModel
import br.com.moov.app.core.UiEvent
import br.com.moov.app.core.UiModel
import br.com.moov.app.movies.MoviesUiEvent.EnterScreenUiEvent
import br.com.moov.app.movies.MoviesUiEvent.FinishedScrollingUiEvent
import br.com.moov.app.movies.MoviesUiEvent.MovieFavoritedUiEvent
import br.com.moov.app.util.logd
import br.com.moov.domain.movie.Movie
import br.com.moov.domain.movie.MovieBookmarkInteractor
import br.com.moov.domain.movie.MoviesInteractor
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
    private val moviesInteractor: MoviesInteractor,
    private val bookmarkInteractor: MovieBookmarkInteractor
) : BaseViewModel<MoviesUiEvent, MoviesUiModel>() {

    private val movies = mutableListOf<Movie>()

    private var currentPage = 1

    override suspend fun processUiEvent(uiEvent: MoviesUiEvent) {
        logd { "Processing ui event $uiEvent" }
        when (uiEvent) {
            is EnterScreenUiEvent -> {
                handleEnterScreen()
            }
            is FinishedScrollingUiEvent -> {
                handleFinishedScrolling()
            }
            is MovieFavoritedUiEvent -> {
                handleMovieFavorited(uiEvent)
            }
        }
    }

    private suspend fun handleEnterScreen() {
        if (movies.isEmpty()) {
            currentPage = 1
            emitUiModel(MoviesUiModel(true))
            val result = runCatching { appendMovies(moviesInteractor.getPopularMovies()) }
            emitUiModel(MoviesUiModel(movies = movies, error = result.exceptionOrNull()))
        } else {
            val result = runCatching {
                val updatedMovies = bookmarkInteractor.getBookmarkedMovies(movies)
                movies.clear()
                movies.addAll(updatedMovies)
            }
            emitUiModel(MoviesUiModel(movies = movies, error = result.exceptionOrNull()))
        }
    }

    private suspend fun handleFinishedScrolling() {
        if (movies.size < LIMIT_MOVIES) {
            emitUiModel(MoviesUiModel(true, movies))
            val result = runCatching {
                appendMovies(moviesInteractor.getPopularMovies(++currentPage))
            }
            emitUiModel(MoviesUiModel(movies = movies, error = result.exceptionOrNull()))
        }
    }

    private suspend fun handleMovieFavorited(uiEvent: MovieFavoritedUiEvent) {
        runCatching {
            if (uiEvent.favorited) {
                bookmarkInteractor.bookmarkMovie(uiEvent.movie)
                movies.indexOfFirst { it.id == uiEvent.movie.id }
                    .takeUnless { it == -1 }
                    ?.let {
                        val newValue = uiEvent.movie.copy(isBookmarked = true)
                        movies.set(it, newValue)
                    }
            } else {
                bookmarkInteractor.unbookmarkMovie(uiEvent.movie.id!!)
                movies.indexOfFirst { it.id == uiEvent.movie.id }
                    .takeUnless { it == -1 }
                    ?.let {
                        movies.set(it, uiEvent.movie.copy(isBookmarked = false))
                    }
            }
        }
        emitUiModel(MoviesUiModel(movies = movies))
    }

    private fun appendMovies(incomingMovies: List<Movie>) {
        if (incomingMovies.size + movies.size <= LIMIT_MOVIES) {
            movies.addAll(incomingMovies)
        } else {
            movies.addAll(incomingMovies.take(LIMIT_MOVIES - movies.size))
        }
    }

    companion object {
        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        const val LIMIT_MOVIES = 50
    }
}

sealed class MoviesUiEvent : UiEvent() {
    object EnterScreenUiEvent : MoviesUiEvent()
    object FinishedScrollingUiEvent : MoviesUiEvent()
    data class MovieFavoritedUiEvent(val movie: Movie, val favorited: Boolean) : MoviesUiEvent()
}

class MoviesUiModel(
    val loading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    error: Throwable? = null
) : UiModel(error)

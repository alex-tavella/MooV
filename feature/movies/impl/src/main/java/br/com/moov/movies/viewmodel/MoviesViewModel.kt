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
package br.com.moov.movies.viewmodel

import br.com.core.android.BaseViewModel
import br.com.core.android.UiEvent
import br.com.core.android.UiModel
import br.com.core.android.logd
import br.com.moov.bookmark.movie.BookmarkMovieUseCase
import br.com.moov.bookmark.movie.UnBookmarkMovieUseCase
import br.com.moov.movies.domain.GetMoviesUseCase
import br.com.moov.movies.domain.Movie
import javax.inject.Inject

internal class MoviesViewModel @Inject constructor(
    private val getMovies: GetMoviesUseCase,
    private val bookmarkMovie: BookmarkMovieUseCase,
    private val unbookmarkMovie: UnBookmarkMovieUseCase
) : BaseViewModel<MoviesUiEvent, MoviesUiModel>() {

    private val movies = mutableListOf<Movie>()

    private var currentPage = 1

    override suspend fun processUiEvent(uiEvent: MoviesUiEvent) {
        logd { "Processing ui event $uiEvent" }
        when (uiEvent) {
            is MoviesUiEvent.EnterScreenUiEvent -> {
                handleEnterScreen()
            }
            is MoviesUiEvent.FinishedScrollingUiEvent -> {
                handleFinishedScrolling()
            }
            is MoviesUiEvent.MovieFavoritedUiEvent -> {
                handleMovieFavorited(uiEvent)
            }
        }
    }

    private suspend fun handleEnterScreen() {
        if (movies.isEmpty()) {
            currentPage = 1
            emitUiModel(MoviesUiModel(true))
            val result = runCatching { appendMovies(getMovies(currentPage)) }
            emitUiModel(MoviesUiModel(movies = movies, error = result.exceptionOrNull()))
        }
    }

    private suspend fun handleFinishedScrolling() {
        if (movies.size < LIMIT_MOVIES) {
            emitUiModel(MoviesUiModel(true, movies))
            val result = runCatching {
                appendMovies(getMovies(++currentPage))
            }
            emitUiModel(MoviesUiModel(movies = movies, error = result.exceptionOrNull()))
        }
    }

    private suspend fun handleMovieFavorited(uiEvent: MoviesUiEvent.MovieFavoritedUiEvent) {
        runCatching {
            if (uiEvent.favorited) {
                bookmarkMovie(uiEvent.movie.id)
                movies.indexOfFirst { it.id == uiEvent.movie.id }
                    .takeUnless { it == -1 }
                    ?.let {
                        val newValue = uiEvent.movie.copy(isBookmarked = true)
                        movies.set(it, newValue)
                    }
            } else {
                unbookmarkMovie(uiEvent.movie.id)
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
        const val LIMIT_MOVIES = 50
    }
}

internal sealed class MoviesUiEvent : UiEvent() {
    object EnterScreenUiEvent : MoviesUiEvent()
    object FinishedScrollingUiEvent : MoviesUiEvent()
    data class MovieFavoritedUiEvent(val movie: Movie, val favorited: Boolean) : MoviesUiEvent()
}

internal class MoviesUiModel(
    val loading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    error: Throwable? = null
) : UiModel(error)

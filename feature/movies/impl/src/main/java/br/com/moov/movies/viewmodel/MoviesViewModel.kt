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

import androidx.lifecycle.ViewModel
import br.com.core.android.BaseViewModel
import br.com.core.android.UiEvent
import br.com.core.android.UiModel
import br.com.core.android.ViewModelKey
import br.com.core.android.logd
import br.com.moov.bookmark.movie.BookmarkMovieUseCase
import br.com.moov.bookmark.movie.UnBookmarkMovieUseCase
import br.com.moov.movies.di.MoviesScope
import br.com.moov.movies.domain.GetMoviesUseCase
import br.com.moov.movies.domain.Movie
import com.squareup.anvil.annotations.ContributesMultibinding
import javax.inject.Inject

private const val ERROR_MESSAGE_DEFAULT = "An error has occurred, please try again later"
private const val LIMIT_MOVIES = 50

@ContributesMultibinding(MoviesScope::class, ViewModel::class)
@ViewModelKey(MoviesViewModel::class)
class MoviesViewModel @Inject constructor(
    private val getMovies: GetMoviesUseCase,
    private val bookmarkMovie: BookmarkMovieUseCase,
    private val unBookmarkMovie: UnBookmarkMovieUseCase
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
            getMovies(currentPage)
                .onSuccess {
                    appendMovies(it)
                    emitUiModel(MoviesUiModel(movies = movies, error = null))
                }
                .onError {
                    emitUiModel(MoviesUiModel(movies = movies, error = getMoviesError()))
                }
        }
    }

    private suspend fun handleFinishedScrolling() {
        if (movies.size < LIMIT_MOVIES) {
            emitUiModel(MoviesUiModel(true, movies))
            getMovies(++currentPage)
                .onSuccess {
                    appendMovies(it)
                    emitUiModel(MoviesUiModel(movies = movies, error = null))
                }.onError {
                    emitUiModel(MoviesUiModel(movies = movies, error = getMoviesError()))
                }
        }
    }

    private fun getMoviesError(): Throwable {
        return Exception(ERROR_MESSAGE_DEFAULT)
    }

    private suspend fun handleMovieFavorited(uiEvent: MoviesUiEvent.MovieFavoritedUiEvent) {
        if (uiEvent.favorited) {
            bookmarkMovie(uiEvent.movie.id)
                .onSuccess {
                    onMovieBookmarked(uiEvent.movie)
                    emitUiModel(MoviesUiModel(movies = movies))
                }
        } else {
            unBookmarkMovie(uiEvent.movie.id)
                .onSuccess {
                    onMovieUnBookmarked(uiEvent.movie)
                    emitUiModel(MoviesUiModel(movies = movies))
                }
        }
    }

    private fun onMovieBookmarked(movie: Movie) {
        movies.indexOfFirst { it.id == movie.id }
            .takeUnless { it == -1 }
            ?.let {
                val newValue = movie.copy(isBookmarked = true)
                movies[it] = newValue
            }
    }

    private fun onMovieUnBookmarked(movie: Movie) {
        movies.indexOfFirst { it.id == movie.id }
            .takeUnless { it == -1 }
            ?.let {
                movies[it] = movie.copy(isBookmarked = false)
            }
    }

    private fun appendMovies(incomingMovies: List<Movie>) {
        if (incomingMovies.size + movies.size <= LIMIT_MOVIES) {
            movies.addAll(incomingMovies)
        } else {
            movies.addAll(incomingMovies.take(LIMIT_MOVIES - movies.size))
        }
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

package br.com.moov.app.movies

import androidx.annotation.VisibleForTesting
import br.com.moov.app.core.BaseViewModel
import br.com.moov.app.core.UiEvent
import br.com.moov.app.core.UiModel
import br.com.moov.app.util.logd
import br.com.moov.domain.movie.Movie
import br.com.moov.domain.movie.MovieBookmarkInteractor
import br.com.moov.domain.movie.MoviesInteractor

class MoviesViewModel(
    private val moviesInteractor: MoviesInteractor,
    private val bookmarkInteractor: MovieBookmarkInteractor) : BaseViewModel<MoviesUiModel>() {

  private val movies = mutableListOf<Movie>()

  private var currentPage = 1

  override suspend fun processUiEvent(uiEvent: UiEvent) {
    logd { "Processing ui event $uiEvent" }
    when (uiEvent) {
      is EnterScreenUiEvent -> {
        if (movies.isEmpty()) {
          currentPage = 1
          emitUiModel(MoviesUiModel(true))
          val result = kotlin.runCatching { appendMovies(moviesInteractor.getPopularMovies()) }
          emitUiModel(MoviesUiModel(movies = movies, error = result.exceptionOrNull()))
        } else {
          val result = kotlin.runCatching {
            val updatedMovies = bookmarkInteractor.getBookmarkedMovies(movies)
            movies.clear()
            movies.addAll(updatedMovies)
          }

          emitUiModel(MoviesUiModel(movies = movies, error = result.exceptionOrNull()))
        }
      }
      is FinishedScrollingUiEvent -> {
        if (movies.size < LIMIT_MOVIES) {
          emitUiModel(MoviesUiModel(true, movies))
          val result = kotlin.runCatching {
            appendMovies(moviesInteractor.getPopularMovies(++currentPage))
          }
          emitUiModel(MoviesUiModel(movies = movies, error = result.exceptionOrNull()))
        }
      }
      is MovieFavoritedUiEvent -> {
        kotlin.runCatching {
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
    }
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

object EnterScreenUiEvent : UiEvent()
object FinishedScrollingUiEvent : UiEvent()
data class MovieFavoritedUiEvent(val movie: Movie, val favorited: Boolean) : UiEvent()

class MoviesUiModel(
    val loading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    error: Throwable? = null) : UiModel(error)
package br.com.moov.app.movie

import br.com.moov.app.core.BaseViewModel
import br.com.moov.app.core.UiEvent
import br.com.moov.app.core.UiModel
import br.com.moov.app.util.logd
import br.com.moov.domain.movie.Movie
import br.com.moov.domain.movie.MovieInteractor

class MoviesViewModel(
    private val movieInteractor: MovieInteractor) : BaseViewModel<MoviesUiModel>() {

  private val movies = mutableListOf<Movie>()

  private var currentPage = 1

  override suspend fun processUiEvent(uiEvent: UiEvent) {
    logd { "Processing ui event $uiEvent" }
    when (uiEvent) {
      is EnterScreenUiEvent -> {
        if (movies.isEmpty()) {
          currentPage = 1
          emitUiModel(MoviesUiModel(true))
          movies.addAll(movieInteractor.getMovies())
          emitUiModel(MoviesUiModel(movies = movies))
        } else {
          emitUiModel(MoviesUiModel(movies = movies))
        }
      }
      is FinishedScrollingUiEvent -> {
        emitUiModel(MoviesUiModel(true, movies))
        movies.addAll(movieInteractor.getMovies(++currentPage))
        emitUiModel(MoviesUiModel(movies = movies))
      }
    }
  }
}

class EnterScreenUiEvent : UiEvent()

class FinishedScrollingUiEvent : UiEvent()

data class MoviesUiModel(
    val loading: Boolean = false,
    val movies: List<Movie> = emptyList()) : UiModel()
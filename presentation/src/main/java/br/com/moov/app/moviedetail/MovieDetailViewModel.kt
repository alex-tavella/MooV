package br.com.moov.app.moviedetail

import br.com.moov.app.core.BaseViewModel
import br.com.moov.app.core.UiEvent
import br.com.moov.app.core.UiModel
import br.com.moov.app.moviedetail.MovieDetailUiEvent.EnterScreen
import br.com.moov.app.moviedetail.MovieDetailUiEvent.MovieFavoritedUiEvent
import br.com.moov.domain.movie.MovieBookmarkInteractor
import br.com.moov.domain.movie.MovieDetail
import br.com.moov.domain.movie.MovieDetailInteractor

class MovieDetailViewModel(
    private val movieDetailInteractor: MovieDetailInteractor,
    private val bookmarkInteractor: MovieBookmarkInteractor
) : BaseViewModel<MovieDetailUiEvent, MovieDetailUiModel>() {

  override suspend fun processUiEvent(uiEvent: MovieDetailUiEvent) {
    when (uiEvent) {
      is EnterScreen -> {
        handleEnterScreen(uiEvent)
      }
      is MovieFavoritedUiEvent -> {
        handleMovieFavorited(uiEvent)
      }
    }
  }

  private suspend fun handleEnterScreen(uiEvent: EnterScreen) {
    emitUiModel(MovieDetailUiModel(loading = true))
    val result = runCatching {
      movieDetailInteractor.getMovieDetail(uiEvent.movieId)
    }
    emitUiModel(MovieDetailUiModel(loading = false, movie = result.getOrNull(),
        error = result.exceptionOrNull()))
  }

  private suspend fun handleMovieFavorited(uiEvent: MovieFavoritedUiEvent) {
    val result = runCatching {
      if (uiEvent.favorited) {
        bookmarkInteractor.bookmarkMovie(uiEvent.movie)
        uiEvent.movie.copy(isBookmarked = true)
      } else {
        bookmarkInteractor.unbookmarkMovie(uiEvent.movie.id!!)
        uiEvent.movie.copy(isBookmarked = false)
      }
    }
    emitUiModel(
        MovieDetailUiModel(movie = result.getOrNull(), error = result.exceptionOrNull()))
  }
}

class MovieDetailUiModel(
    val loading: Boolean = false,
    val movie: MovieDetail? = null,
    error: Throwable? = null) : UiModel(error)

sealed class MovieDetailUiEvent : UiEvent() {
  class EnterScreen(val movieId: Int) : MovieDetailUiEvent()
  data class MovieFavoritedUiEvent(
      val movie: MovieDetail,
      val favorited: Boolean
  ) : MovieDetailUiEvent()
}

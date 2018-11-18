package br.com.moov.app.moviedetail

import br.com.moov.app.core.BaseViewModel
import br.com.moov.app.core.UiEvent
import br.com.moov.app.core.UiModel
import br.com.moov.domain.movie.MovieBookmarkInteractor
import br.com.moov.domain.movie.MovieDetail
import br.com.moov.domain.movie.MovieDetailInteractor

class MovieDetailViewModel(
    private val movieDetailInteractor: MovieDetailInteractor,
    private val bookmarkInteractor: MovieBookmarkInteractor) : BaseViewModel<MovieDetailUiModel>() {

  override suspend fun processUiEvent(uiEvent: UiEvent) {
    when (uiEvent) {
      is EnterScreen -> {
        emitUiModel(MovieDetailUiModel(loading = true))
        val result = kotlin.runCatching {
          movieDetailInteractor.getMovieDetail(uiEvent.movieId)
        }
        emitUiModel(MovieDetailUiModel(loading = false, movie = result.getOrNull(),
            error = result.exceptionOrNull()))
      }
      is MovieFavoritedUiEvent -> {
        val result = kotlin.runCatching {
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
  }
}

class MovieDetailUiModel(
    val loading: Boolean = false,
    val movie: MovieDetail? = null,
    error: Throwable? = null) : UiModel(error)

class EnterScreen(val movieId: Int) : UiEvent()
data class MovieFavoritedUiEvent(val movie: MovieDetail, val favorited: Boolean) : UiEvent()
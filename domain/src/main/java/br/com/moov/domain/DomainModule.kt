package br.com.moov.domain

import br.com.moov.domain.movie.MovieBookmarkInteractor
import br.com.moov.domain.movie.MovieBookmarkInteractorImpl
import br.com.moov.domain.movie.MovieDetailInteractor
import br.com.moov.domain.movie.MovieDetailInteractorImpl
import br.com.moov.domain.movie.MoviesInteractor
import br.com.moov.domain.movie.MoviesInteractorImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DomainModule {
  @[Binds Singleton]
  fun bindsMoviesInteractor(moviesInteractor: MoviesInteractorImpl): MoviesInteractor

  @[Binds Singleton]
  fun bindsMovieDetailInteractor(
      movieDetailInteractor: MovieDetailInteractorImpl
  ): MovieDetailInteractor

  @[Binds Singleton]
  fun bindsMovieBookmarkInteractor(
      moviesBookmarkInteractor: MovieBookmarkInteractorImpl
  ): MovieBookmarkInteractor
}

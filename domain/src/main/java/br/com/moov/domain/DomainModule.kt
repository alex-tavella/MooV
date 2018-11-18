package br.com.moov.domain

import br.com.moov.domain.movie.MovieBookmarkInteractor
import br.com.moov.domain.movie.MovieBookmarkInteractorImpl
import br.com.moov.domain.movie.MovieDetailInteractor
import br.com.moov.domain.movie.MovieDetailInteractorImpl
import br.com.moov.domain.movie.MoviesInteractor
import br.com.moov.domain.movie.MoviesInteractorImpl
import org.koin.dsl.module.module

fun domainModule() = module {
  single { MoviesInteractorImpl(get()) } bind MoviesInteractor::class
  single { MovieDetailInteractorImpl(get()) } bind MovieDetailInteractor::class
  single { MovieBookmarkInteractorImpl(get()) } bind MovieBookmarkInteractor::class
}
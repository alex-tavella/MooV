package br.com.moov.domain

import br.com.moov.domain.movie.MovieInteractor
import br.com.moov.domain.movie.MovieInteractorImpl
import org.koin.dsl.module.module

fun domainModule() = module {
  single { MovieInteractorImpl(get()) } bind MovieInteractor::class
}
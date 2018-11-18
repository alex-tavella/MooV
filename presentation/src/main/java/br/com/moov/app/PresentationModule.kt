package br.com.moov.app

import br.com.moov.app.moviedetail.MovieDetailViewModel
import br.com.moov.app.movies.MoviesViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

fun presentationModule() = module {
  viewModel { MoviesViewModel(get(), get()) }
  viewModel { MovieDetailViewModel(get(), get()) }
}
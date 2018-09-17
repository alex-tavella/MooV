package br.com.moov.app

import br.com.moov.app.movie.MoviesViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

fun presentationModule() = module {
  viewModel { MoviesViewModel(get()) }
}
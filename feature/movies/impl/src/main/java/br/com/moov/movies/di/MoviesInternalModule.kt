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
package br.com.moov.movies.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.core.android.DefaultViewModelProviderFactory
import br.com.core.android.ViewModelKey
import br.com.moov.movies.data.DefaultMoviesRepository
import br.com.moov.movies.data.MovieDataSource
import br.com.moov.movies.data.remote.TMDBMovieDataSource
import br.com.moov.movies.data.remote.TmdbMoviesApi
import br.com.moov.movies.domain.GetMovies
import br.com.moov.movies.domain.GetMoviesUseCase
import br.com.moov.movies.domain.MoviesRepository
import br.com.moov.movies.viewmodel.MoviesViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@Module(
    includes = [
        MoviesDataModule::class,
        MoviesDomainModule::class,
        MoviesViewModelModule::class,
        MoviesViewModule::class
    ]
)
internal interface MoviesInternalModule

@Module
internal interface MoviesDataModule {

    @[Binds]
    fun bindsMoviesDataSource(moviesRemoteDataSource: TMDBMovieDataSource): MovieDataSource

    @[Binds]
    fun bindsMoviesRepository(moviesRepository: DefaultMoviesRepository): MoviesRepository

    companion object {

        @[Provides]
        fun providesApi(retrofit: Retrofit): TmdbMoviesApi =
            retrofit.create(TmdbMoviesApi::class.java)
    }
}

@Module
internal interface MoviesDomainModule {
    @[Binds]
    fun bindsGetMoviesUseCase(getMovies: GetMovies): GetMoviesUseCase
}

@Module
internal interface MoviesViewModelModule {

    @[Binds IntoMap ViewModelKey(MoviesViewModel::class)]
    fun bindsMoviesViewModel(moviesViewModel: MoviesViewModel): ViewModel

    @[Binds]
    fun bindsDefaultViewModelProviderFactory(
        viewModelProviderFactory: DefaultViewModelProviderFactory
    ): ViewModelProvider.Factory
}

@Module
internal interface MoviesViewModule

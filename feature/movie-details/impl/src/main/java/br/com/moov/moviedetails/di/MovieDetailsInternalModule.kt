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
package br.com.moov.moviedetails.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.core.android.DefaultViewModelProviderFactory
import br.com.core.android.ViewModelKey
import br.com.moov.moviedetails.data.DefaultMovieDetailRepository
import br.com.moov.moviedetails.data.MovieDetailDataSource
import br.com.moov.moviedetails.data.remote.TMDBDMovieDetailApi
import br.com.moov.moviedetails.data.remote.TmdbMovieDetailDataSource
import br.com.moov.moviedetails.domain.GetMovieDetail
import br.com.moov.moviedetails.domain.GetMovieDetailUseCase
import br.com.moov.moviedetails.domain.MovieDetailRepository
import br.com.moov.moviedetails.viewmodel.MovieDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@Module(
    includes = [
        MovieDetailsDataModule::class,
        MovieDetailsDomainModule::class,
        MovieDetailsViewModelModule::class,
        MovieDetailsViewModule::class
    ]
)
@InstallIn(ActivityRetainedComponent::class)
internal interface MovieDetailsInternalModule

@Module
@InstallIn(ActivityRetainedComponent::class)
internal interface MovieDetailsDataModule {

    @Binds
    fun bindsMovieDetailDataSource(
        tmdbMovieDetailDataSource: TmdbMovieDetailDataSource
    ): MovieDetailDataSource

    @Binds
    fun bindsMovieDetailRepository(
        movieDetailRepository: DefaultMovieDetailRepository
    ): MovieDetailRepository

    companion object {
        @[Provides]
        fun providesMovieDetailsApi(retrofit: Retrofit): TMDBDMovieDetailApi {
            return retrofit.create(TMDBDMovieDetailApi::class.java)
        }
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
internal interface MovieDetailsDomainModule {

    @Binds
    fun bindsGetMovieDetailUseCase(getMovieDetail: GetMovieDetail): GetMovieDetailUseCase
}

@Module
@InstallIn(ActivityRetainedComponent::class)
internal interface MovieDetailsViewModelModule {
    @[Binds IntoMap ViewModelKey(MovieDetailViewModel::class)]
    fun bindsMoviesViewModel(movieDetailViewModel: MovieDetailViewModel): ViewModel

    @[Binds]
    fun bindsDefaultViewModelProviderFactory(
        viewModelProviderFactory: DefaultViewModelProviderFactory
    ): ViewModelProvider.Factory
}

@Module
@InstallIn(ActivityRetainedComponent::class)
internal interface MovieDetailsViewModule

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

import androidx.lifecycle.ViewModelProvider
import br.com.core.android.DefaultViewModelProviderFactory
import br.com.moov.moviedetails.data.remote.TMDBMovieDetailApi
import com.squareup.anvil.annotations.ContributesTo
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(
    includes = [
        MovieDetailsDataModule::class,
        MovieDetailsViewModelModule::class,
    ]
)
@ContributesTo(MovieDetailScope::class)
interface MovieDetailsInternalModule

@Module
internal object MovieDetailsDataModule {
    @Provides
    fun providesMovieDetailsApi(retrofit: Retrofit): TMDBMovieDetailApi {
        return retrofit.create(TMDBMovieDetailApi::class.java)
    }
}

@Module
internal interface MovieDetailsViewModelModule {
    @Binds
    fun bindsDefaultViewModelProviderFactory(
        viewModelProviderFactory: DefaultViewModelProviderFactory
    ): ViewModelProvider.Factory
}

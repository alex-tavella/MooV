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

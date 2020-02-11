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

import br.com.moov.bookmark.movie.BookmarkMovieUseCase
import br.com.moov.bookmark.movie.UnBookmarkMovieUseCase
import br.com.moov.core.ImageUrlResolver
import br.com.moov.moviedetails.navigation.MovieDetailsNavigator
import br.com.moov.movies.view.MoviesFragment
import dagger.Component
import retrofit2.Retrofit

@Component(
    modules = [MoviesInternalModule::class],
    dependencies = [MoviesDependencies::class]
)
internal interface MoviesComponent {

    fun inject(moviesFragment: MoviesFragment)

    @Component.Factory
    interface Factory {
        fun create(dependencies: MoviesDependencies): MoviesComponent
    }
}

interface MoviesDependencies {
    fun retrofit(): Retrofit
    fun imageUrlResolver(): ImageUrlResolver
    fun bookmarkUseCase(): BookmarkMovieUseCase
    fun unBookmarkUseCase(): UnBookmarkMovieUseCase
    fun movieDetailNavigator(): MovieDetailsNavigator
}

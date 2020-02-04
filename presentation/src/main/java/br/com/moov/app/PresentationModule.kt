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
package br.com.moov.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.moov.app.core.DefaultViewModelProviderFactory
import br.com.moov.app.moviedetail.MovieDetailViewModel
import br.com.moov.app.movies.MoviesViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.annotation.AnnotationTarget.PROPERTY_GETTER
import kotlin.annotation.AnnotationTarget.PROPERTY_SETTER
import kotlin.reflect.KClass

@Target(FUNCTION, PROPERTY_GETTER, PROPERTY_SETTER)
@Retention(RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
interface PresentationModule {

    @[Binds IntoMap ViewModelKey(MoviesViewModel::class)]
    fun bindsMoviesViewModel(moviesViewModel: MoviesViewModel): ViewModel

    @[Binds IntoMap ViewModelKey(MovieDetailViewModel::class)]
    fun bindsMovieDetailViewModel(movieDetailViewModel: MovieDetailViewModel): ViewModel

    @[Binds Singleton]
    fun bindsViewModelFactory(factory: DefaultViewModelProviderFactory): ViewModelProvider.Factory
}

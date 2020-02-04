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

import android.content.Context
import br.com.moov.app.moviedetail.MovieDetailActivity
import br.com.moov.app.movies.MoviesFragment
import br.com.moov.data.DataModule
import br.com.moov.domain.DomainModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DomainModule::class,
        DataModule::class,
        PresentationModule::class]
)
interface AppComponent {

    fun inject(movieDetailActivity: MovieDetailActivity)
    fun inject(moviesFragment: MoviesFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}

interface AppComponentProvider {
    val appComponent: AppComponent
}

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

import br.com.moov.dibridge.CoreComponent
import br.com.moov.moviedetails.view.MovieDetailActivity
import dagger.Component

@Component(
    modules = [MovieDetailsInternalModule::class],
    dependencies = [CoreComponent::class]
)
internal interface MovieDetailComponent {

    fun inject(movieDetailActivity: MovieDetailActivity)

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): MovieDetailComponent
    }
}

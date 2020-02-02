/*
 *     Copyright 2017 Alex Almeida Tavella
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package br.com.alex.moov.androidapp.base.di.home

import android.content.Context
import br.com.alex.moov.androidapp.base.di.ActivityModule
import br.com.alex.moov.androidapp.base.di.ActivityScope
import br.com.alex.moov.androidapp.base.di.ApplicationContextQualifier
import br.com.alex.moov.androidapp.home.HomeActivity
import br.com.alex.moov.androidapp.home.HomeScreenSwitcher
import br.com.alex.moov.androidapp.home.list.MovieAdapter
import br.com.alex.moov.androidapp.home.list.MoviesViewModel
import br.com.alex.moov.domain.interactor.DiscoverMoviesInteractor
import dagger.Module
import dagger.Provides

@Module
class HomeModule(private val homeActivity: HomeActivity) : ActivityModule(homeActivity) {

  @Provides
  @ActivityScope
  fun providesScreenSwitcher() = HomeScreenSwitcher(homeActivity)

  @Provides
  fun providesNewAdapter(@ApplicationContextQualifier appContext: Context,
      screenSwitcher: HomeScreenSwitcher) = MovieAdapter(
      appContext, screenSwitcher)

  @Provides
  fun providesNewViewModel(movieAdapter: MovieAdapter,
      discoverMoviesInteractor: DiscoverMoviesInteractor): MoviesViewModel = MoviesViewModel(
      movieAdapter, discoverMoviesInteractor)
}
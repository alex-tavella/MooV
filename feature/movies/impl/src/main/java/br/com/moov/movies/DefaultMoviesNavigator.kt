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
package br.com.moov.movies

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN
import br.com.moov.core.di.AppScope
import br.com.moov.movies.navigation.MoviesNavigator
import br.com.moov.movies.view.MoviesFragment
import com.squareup.anvil.annotations.ContributesBinding
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class DefaultMoviesNavigator @Inject constructor() : MoviesNavigator {

    override fun openMoviesScreen(fragmentManager: FragmentManager, containerId: Int) {
        fragmentManager
            .beginTransaction()
            .replace(containerId, MoviesFragment())
            .setTransition(TRANSIT_FRAGMENT_OPEN)
            .commit()
    }
}

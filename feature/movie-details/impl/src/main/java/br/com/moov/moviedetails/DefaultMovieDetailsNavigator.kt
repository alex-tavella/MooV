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
package br.com.moov.moviedetails

import android.view.View
import androidx.fragment.app.FragmentActivity
import br.com.moov.core.AppScope
import br.com.moov.moviedetails.navigation.MovieDetailsNavigator
import br.com.moov.moviedetails.view.MovieDetailActivity
import com.squareup.anvil.annotations.ContributesBinding
import javax.inject.Inject
import javax.inject.Singleton

@ContributesBinding(AppScope::class)
class DefaultMovieDetailsNavigator @Inject constructor() : MovieDetailsNavigator {

    override fun openMovieDetailsScreen(
        activity: FragmentActivity,
        movieId: Int,
        transitionView: View?
    ) {
        MovieDetailActivity.start(activity, movieId, transitionView)
    }
}

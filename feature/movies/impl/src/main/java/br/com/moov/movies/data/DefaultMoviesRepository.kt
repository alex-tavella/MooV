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
package br.com.moov.movies.data

import br.com.moov.movies.data.remote.MoviesResponseMapper
import br.com.moov.movies.di.MoviesScope
import br.com.moov.movies.domain.Movie
import br.com.moov.movies.domain.MoviesRepository
import com.squareup.anvil.annotations.ContributesBinding
import javax.inject.Inject

@ContributesBinding(MoviesScope::class)
class DefaultMoviesRepository @Inject constructor(
    private val movieDataSource: MovieDataSource,
    private val movieMapper: MoviesResponseMapper
) : MoviesRepository {

    override suspend fun getMovies(page: Int): List<Movie> {
        return movieDataSource.getMovies(
            page, SORT_ORDER_POPULARITY,
            THRESHOLD_VOTE_COUNT
        ).map { movieMapper.map(it) }
    }

    companion object {
        const val SORT_ORDER_POPULARITY = "popularity.desc"
        const val THRESHOLD_VOTE_COUNT = 100
    }
}

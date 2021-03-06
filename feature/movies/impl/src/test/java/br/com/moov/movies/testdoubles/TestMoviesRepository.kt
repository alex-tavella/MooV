/*
 * Copyright 2021 Alex Almeida Tavella
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
package br.com.moov.movies.testdoubles

import br.com.moov.core.result.Result
import br.com.moov.movies.domain.GetMoviesError
import br.com.moov.movies.domain.Movie
import br.com.moov.movies.domain.MoviesRepository

class TestMoviesRepository(
    private val movies: List<Movie> = emptyList(),
    private val pageSize: Int = 3
) : MoviesRepository {
    override suspend fun getMovies(page: Int): Result<List<Movie>, GetMoviesError> {
        return movies.take(pageSize).takeIf { it.isNotEmpty() }
            ?.let { Result.Ok(it) } ?: Result.Err(GetMoviesError)
    }
}

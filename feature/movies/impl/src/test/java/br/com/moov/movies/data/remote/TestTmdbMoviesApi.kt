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
package br.com.moov.movies.data.remote

import java.io.IOException

class TestTmdbMoviesApi(
    private val movies: List<TmdbMovie> = emptyList(),
    private val pageSize: Int = 3,
) : TmdbMoviesApi {

    override suspend fun discoverMovies(
        page: Int,
        sortBy: String,
        voteCount: Int
    ): MovieDiscoverResponse {
        return if (movies.isNotEmpty()) {
            val filterResult = movies
                .filter { it.voteCount >= voteCount }
                .sortedByDescending {
                    if (sortBy == "popularity.desc") it.popularity.toInt()
                    else it.voteCount
                }
            MovieDiscoverResponse(
                page = page,
                results = filterResult.take(pageSize),
                totalResults = filterResult.size,
                totalPages = filterResult.size / pageSize
            )
        } else {
            throw IOException("404: Not found")
        }
    }
}

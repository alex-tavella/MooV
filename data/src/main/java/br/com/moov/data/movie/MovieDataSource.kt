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
package br.com.moov.data.movie

import br.com.moov.data.common.await
import br.com.moov.data.movie.tmdb.ImageConfigurations
import br.com.moov.data.movie.tmdb.TMDBDApi
import br.com.moov.data.movie.tmdb.TMDBMovie
import br.com.moov.data.movie.tmdb.TMDBMovieDetail
import javax.inject.Inject

interface MovieDataSource {
    suspend fun getMovies(page: Int, sortBy: String, voteCount: Int): List<TMDBMovie>
    suspend fun getMovieDetail(id: Int): TMDBMovieDetail
    suspend fun getImageConfigs(): ImageConfigurations
}

class TMDBMovieDataSource @Inject constructor(private val tmdbdApi: TMDBDApi) : MovieDataSource {
    override suspend fun getMovies(page: Int, sortBy: String, voteCount: Int): List<TMDBMovie> =
        tmdbdApi.discoverMovies(page, sortBy, voteCount).await().results

    override suspend fun getMovieDetail(id: Int): TMDBMovieDetail = tmdbdApi.getMovie(id).await()

    override suspend fun getImageConfigs() = tmdbdApi.getConfiguration().await().images
}

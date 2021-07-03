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
package br.com.moov.movies.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbMoviesApi {

    @GET("/3/discover/movie")
    suspend fun discoverMovies(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String,
        @Query("vote_count.gte") voteCount: Int
    ): MovieDiscoverResponse
}

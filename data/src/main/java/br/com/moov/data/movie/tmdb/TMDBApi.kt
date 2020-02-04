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
package br.com.moov.data.movie.tmdb

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBDApi {

    @GET("/3/discover/movie")
    fun discoverMovies(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String,
        @Query("vote_count.gte") voteCount: Int
    ): Call<MovieDiscoverResponse>

    @GET("/3/movie/{movieId}")
    fun getMovie(@Path("movieId") movieId: Int): Call<TMDBMovieDetail>

    @GET("/3/configuration")
    fun getConfiguration(): Call<ConfigurationsResponse>
}

data class ConfigurationsResponse(val images: ImageConfigurations)

data class ImageConfigurations(
    val base_url: String? = null,
    val backdrop_sizes: List<String> = emptyList(),
    val poster_sizes: List<String> = emptyList()
)

data class MovieDiscoverResponse(
    val page: Int,
    val results: List<TMDBMovie>,
    val total_results: Int,
    val total_pages: Int
)

data class TMDBMovie(
    val id: Int? = null,
    val poster_path: String? = null,
    val original_title: String? = null,
    val popularity: Float = 0F,
    val vote_count: Int = 0
)

data class TMDBMovieDetail(
    val id: Int? = null,
    val poster_path: String? = null,
    val adult: Boolean = false,
    val overview: String? = null,
    val release_date: String? = null,
    val original_title: String? = null,
    val original_language: String? = null,
    val title: String? = null,
    val backdrop_path: String? = null,
    val popularity: Float = 0F,
    val vote_count: Int = 0,
    val video: Boolean = false,
    val vote_average: Float = 0F,
    val genres: List<Genre> = emptyList()
)

data class Genre(val id: Int? = null, val name: String? = null)

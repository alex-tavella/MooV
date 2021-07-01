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
package br.com.moov.moviedetails.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class TmdbMovieDetail(
    @Json(name = "id") val id: Int,
    @Json(name = "poster_path") val posterPath: String? = null,
    @Json(name = "adult") val adult: Boolean = false,
    @Json(name = "overview") val overview: String? = null,
    @Json(name = "release_date") val releaseDate: String? = null,
    @Json(name = "original_title") val originalTitle: String? = null,
    @Json(name = "original_language") val originalLanguage: String? = null,
    @Json(name = "title") val title: String? = null,
    @Json(name = "backdrop_path") val backdropPath: String? = null,
    @Json(name = "popularity") val popularity: Float = 0F,
    @Json(name = "vote_count") val voteCount: Int = 0,
    @Json(name = "video") val video: Boolean = false,
    @Json(name = "vote_average") val voteAverage: Float = 0F,
    @Json(name = "genres") val genres: List<Genre> = emptyList()
)

@JsonClass(generateAdapter = true)
internal data class Genre(
    @Json(name = "id") val id: Int? = null,
    @Json(name = "name") val name: String? = null
)

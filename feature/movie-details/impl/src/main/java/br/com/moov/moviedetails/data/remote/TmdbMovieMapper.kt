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

import br.com.moov.core.ImageUrlResolver
import br.com.moov.moviedetails.domain.MovieDetail
import javax.inject.Inject

internal class TmdbMovieMapper @Inject constructor(
    private val imageUrlResolver: ImageUrlResolver
) {
    suspend fun map(tmdbMovie: TmdbMovieDetail): MovieDetail {
        return MovieDetail(
            id = tmdbMovie.id,
            title = tmdbMovie.original_title,
            posterUrl = tmdbMovie.poster_path?.let { imageUrlResolver.getPosterUrl(it) },
            popularity = tmdbMovie.popularity,
            originalLanguage = tmdbMovie.original_language,
            releaseDate = tmdbMovie.release_date,
            overview = tmdbMovie.overview,
            voteAverage = tmdbMovie.vote_average,
            backdropUrl = tmdbMovie.backdrop_path?.let { imageUrlResolver.getBackdropUrl(it) },
            genres = tmdbMovie.genres.mapNotNull { it.name })
    }
}

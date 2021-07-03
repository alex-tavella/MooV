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

import br.com.moov.core.image.ImageUrlResolver
import br.com.moov.moviedetails.domain.MovieDetail
import javax.inject.Inject

class TmdbMovieMapper @Inject constructor(
    private val imageUrlResolver: ImageUrlResolver
) {
    suspend fun map(tmdbMovie: TmdbMovieDetail): MovieDetail {
        return MovieDetail(
            id = tmdbMovie.id,
            title = tmdbMovie.originalTitle,
            posterUrl = tmdbMovie.posterPath?.let { imageUrlResolver.getPosterUrl(it) },
            popularity = tmdbMovie.popularity,
            originalLanguage = tmdbMovie.originalLanguage,
            releaseDate = tmdbMovie.releaseDate,
            overview = tmdbMovie.overview,
            voteAverage = tmdbMovie.voteAverage,
            backdropUrl = tmdbMovie.backdropPath?.let { imageUrlResolver.getBackdropUrl(it) },
            genres = tmdbMovie.genres.mapNotNull { it.name }
        )
    }
}

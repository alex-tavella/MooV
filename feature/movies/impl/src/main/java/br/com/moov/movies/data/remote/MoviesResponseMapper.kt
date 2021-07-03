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

import br.com.moov.core.image.ImageUrlResolver
import br.com.moov.movies.domain.Movie
import javax.inject.Inject

class MoviesResponseMapper @Inject constructor(
    private val imageUrlResolver: ImageUrlResolver
) {
    suspend fun map(tmdbMovie: TmdbMovie): Movie {
        return Movie(
            id = tmdbMovie.id,
            title = tmdbMovie.originalTitle,
            thumbnailUrl = tmdbMovie.posterPath?.let { imageUrlResolver.getPosterUrl(it) }
        )
    }
}

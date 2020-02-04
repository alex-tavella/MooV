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

import br.com.moov.data.movie.bookmark.database.entity.MovieEntity
import br.com.moov.data.movie.tmdb.ImageConfigurations
import br.com.moov.data.movie.tmdb.TMDBMovie
import br.com.moov.data.movie.tmdb.TMDBMovieDetail
import br.com.moov.domain.movie.Movie
import br.com.moov.domain.movie.MovieDetail
import javax.inject.Inject

open class MovieMapper @Inject constructor() {
    open fun map(tmdbMovie: TMDBMovie, imageConfigs: ImageConfigurations): Movie {
        return Movie(
            tmdbMovie.id, tmdbMovie.original_title,
            getPosterUrl(imageConfigs, tmdbMovie.poster_path)
        )
    }

    open fun map(tmdbMovie: TMDBMovieDetail, imageConfigs: ImageConfigurations): MovieDetail {
        return MovieDetail(
            id = tmdbMovie.id,
            title = tmdbMovie.original_title,
            posterUrl = getPosterUrl(imageConfigs, tmdbMovie.poster_path),
            popularity = tmdbMovie.popularity,
            originalLanguage = tmdbMovie.original_language,
            releaseDate = tmdbMovie.release_date,
            overview = tmdbMovie.overview,
            voteAverage = tmdbMovie.vote_average,
            backdropUrl = getBackdropUrl(imageConfigs, tmdbMovie.backdrop_path),
            genres = tmdbMovie.genres.mapNotNull { it.name })
    }

    private fun getPosterUrl(imageConfigs: ImageConfigurations, path: String?): String? {
        if (!imageConfigs.base_url.isNullOrBlank() &&
            !path.isNullOrBlank() &&
            imageConfigs.poster_sizes.isNotEmpty()
        ) {
            return imageConfigs.base_url + selectConfigurationOption(imageConfigs.poster_sizes) + path
        }
        return null
    }

    private fun getBackdropUrl(imageConfigs: ImageConfigurations, path: String?): String? {
        if (!imageConfigs.base_url.isNullOrBlank() &&
            !path.isNullOrBlank() &&
            imageConfigs.backdrop_sizes.isNotEmpty()
        ) {
            return imageConfigs.base_url + selectConfigurationOption(imageConfigs.backdrop_sizes) + path
        }
        return null
    }

    private fun selectConfigurationOption(options: List<String>): String {
        return options[(options.size / 2)]
    }

    fun toEntity(movie: Movie): MovieEntity? {
        return movie.id?.let { MovieEntity(it, movie.title, movie.thumbnailUrl) }
    }

    fun toEntity(movie: MovieDetail): MovieEntity? {
        return movie.id?.let { MovieEntity(it, movie.title, movie.posterUrl) }
    }
}

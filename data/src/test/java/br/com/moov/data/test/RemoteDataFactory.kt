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
package br.com.moov.data.test

import br.com.moov.data.movie.tmdb.ConfigurationsResponse
import br.com.moov.data.movie.tmdb.Genre
import br.com.moov.data.movie.tmdb.ImageConfigurations
import br.com.moov.data.movie.tmdb.MovieDiscoverResponse
import br.com.moov.data.movie.tmdb.TMDBMovie
import br.com.moov.data.movie.tmdb.TMDBMovieDetail
import br.com.moov.test.DataFactory

object RemoteDataFactory {
    fun newMovieResponse() = MovieDiscoverResponse(
        DataFactory.randomInt(),
        newTMDBMoviesList(20),
        DataFactory.randomInt(),
        DataFactory.randomInt()
    )

    fun newTMDBMovie() = TMDBMovie(
        id = DataFactory.randomInt(),
        poster_path = DataFactory.randomPathString(),
        original_title = DataFactory.randomString(),
        popularity = DataFactory.randomScore(),
        vote_count = DataFactory.randomInt()
    )

    fun newTMDBMovieDetail() = TMDBMovieDetail(
        id = DataFactory.randomInt(),
        poster_path = DataFactory.randomPathString(),
        original_title = DataFactory.randomString(),
        title = DataFactory.randomString(),
        adult = DataFactory.randomBoolean(),
        vote_average = DataFactory.randomScore(),
        backdrop_path = DataFactory.randomPathString(),
        original_language = DataFactory.randomString(),
        overview = DataFactory.randomString(),
        popularity = DataFactory.randomScore(),
        release_date = DataFactory.randomDateAsString(),
        video = DataFactory.randomBoolean(),
        vote_count = DataFactory.randomInt(),
        genres = newGenreList()
    )

    fun newGenreList() = mutableListOf<Genre>().apply {
        repeat(DataFactory.randomInt(10)) { add(newGenre()) }
    }

    fun newGenre() =
        Genre(DataFactory.randomInt(), DataFactory.randomString())

    fun newTMDBMoviesList(count: Int) = mutableListOf<TMDBMovie>().apply {
        repeat(count) { add(newTMDBMovie()) }
    }

    fun newConfigurationsResponse() = ConfigurationsResponse(newImageConfigurations())

    fun newImageConfigurations() =
        ImageConfigurations(
            base_url = DataFactory.randomUrl(),
            poster_sizes = newImageConfigOptionList(DataFactory.randomInt()),
            backdrop_sizes = newImageConfigOptionList(DataFactory.randomInt())
        )

    private fun newImageConfigOptionList(count: Int): List<String> {
        return mutableListOf<String>().apply {
            repeat(count) { add(newImageConfigString()) }
        }
    }

    private fun newImageConfigString() = "w${DataFactory.randomInt()}"
}

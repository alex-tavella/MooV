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
package br.com.moov.domain.test

import br.com.moov.domain.movie.Movie
import br.com.moov.domain.movie.MovieDetail
import br.com.moov.test.DataFactory

object DomainDataFactory {
    fun newMovie() =
        Movie(
            DataFactory.randomInt(),
            DataFactory.randomString(),
            DataFactory.randomPathString()
        )

    fun newMovieList(count: Int) =
        mutableListOf<Movie>().apply {
            repeat(count) {
                add(newMovie())
            }
        }

    fun newMovieDetail() =
        MovieDetail(
            id = DataFactory.randomInt(),
            title = DataFactory.randomString(),
            genres = DataFactory.randomStringList(DataFactory.randomInt(5)),
            backdropUrl = DataFactory.randomPathString(),
            posterUrl = DataFactory.randomPathString(),
            voteAverage = DataFactory.randomScore(),
            overview = DataFactory.randomString(),
            releaseDate = DataFactory.randomDateAsString(),
            originalLanguage = DataFactory.randomString(),
            popularity = DataFactory.randomScore()
        )
}

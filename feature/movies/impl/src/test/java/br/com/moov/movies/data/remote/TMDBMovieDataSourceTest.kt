/*
 * Copyright 2021 Alex Almeida Tavella
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

import br.com.moov.core.result.Result
import br.com.moov.movies.domain.Movie
import br.com.moov.movies.testdoubles.TestImageUrlResolver
import br.com.moov.movies.testdoubles.TestTmdbMoviesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class TMDBMovieDataSourceTest {
    private val movies: MutableList<TmdbMovie> = mutableListOf(
        TmdbMovie(123, voteCount = 1000, originalTitle = "Lord of the Rings", popularity = 10f),
        TmdbMovie(234, voteCount = 1000, originalTitle = "Reservoir Dogs", popularity = 9f),
        TmdbMovie(345, voteCount = 1000, originalTitle = "Batman", popularity = 4.3f),
        TmdbMovie(456, voteCount = 1000, originalTitle = "The Joker", popularity = 11f),
        TmdbMovie(567, voteCount = 1000, originalTitle = "Titanic", popularity = 3f),
        TmdbMovie(678, voteCount = 1, originalTitle = "Random Movie", popularity = 1f),
    )
    private val dataSource = TMDBMovieDataSource(
        TestTmdbMoviesApi(movies, 3),
        MoviesResponseMapper(TestImageUrlResolver())
    )

    @Test
    fun getMovies_hasMovies_returnsMovies() = runBlocking {
        val actual = dataSource.getMovies(1)

        val expected = Result.Ok(
            listOf(
                Movie(title = "The Joker", id = 456, thumbnailUrl = null),
                Movie(title = "Lord of the Rings", id = 123, thumbnailUrl = null),
                Movie(title = "Reservoir Dogs", id = 234, thumbnailUrl = null),
            )
        )
        assertEquals(expected, actual)
    }

    @Test
    fun getMovies_endpointFails_returnsError() = runBlocking {
        movies.clear()
        val actual = dataSource.getMovies(1)

        assertTrue(actual.isError())
    }
}

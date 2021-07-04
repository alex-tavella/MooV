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
package br.com.moov.movies.data

import br.com.moov.core.result.Result
import br.com.moov.movies.domain.Movie
import br.com.moov.movies.testdoubles.TestMovieDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class DefaultMoviesRepositoryTest {

    private val movies = mutableListOf(
        Movie(title = "The Joker", id = 123, thumbnailUrl = null),
        Movie(title = "Lord of the Rings", id = 456, thumbnailUrl = null),
        Movie(title = "Reservoir Dogs", id = 789, thumbnailUrl = null),
    )
    private val movieDataSource = TestMovieDataSource(movies)
    private val repository = DefaultMoviesRepository(movieDataSource)

    @Test
    fun getMovies_succeeds_returnsMoviesFromDataSource() = runBlocking {
        val actual = repository.getMovies(1)

        val expected = Result.Ok(movies)
        assertEquals(expected, actual)
    }

    @Test
    fun getMovies_fails_returnsError() = runBlocking {
        movies.clear()
        val actual = repository.getMovies(1)

        assertTrue(actual.isError())
    }
}

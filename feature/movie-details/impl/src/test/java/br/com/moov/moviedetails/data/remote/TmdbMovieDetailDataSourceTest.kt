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
package br.com.moov.moviedetails.data.remote

import br.com.moov.core.result.Result
import br.com.moov.moviedetails.domain.MovieDetail
import br.com.moov.moviedetails.testdoubles.TestImageUrlResolver
import br.com.moov.moviedetails.testdoubles.TestTMDBMovieDetailApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class TmdbMovieDetailDataSourceTest {

    private val apiMovies = listOf(
        TmdbMovieDetail(123),
        TmdbMovieDetail(456),
    )

    private val movieDetailApi = TestTMDBMovieDetailApi(apiMovies)

    private val imageUrlResolver = TestImageUrlResolver()

    private val dataSource =
        TmdbMovieDetailDataSource(movieDetailApi, TmdbMovieMapper(imageUrlResolver))

    @Test
    fun getMovieDetail_apiResponds_returnsFromApi() = runBlocking {
        val actual = dataSource.getMovieDetail(123)

        assertEquals(Result.Ok(MovieDetail(123, null)), actual)
    }

    @Test
    fun getMovieDetail_apiFails_returnsFromApi() = runBlocking {
        val actual = dataSource.getMovieDetail(789)

        assertTrue(actual.isError())
    }
}

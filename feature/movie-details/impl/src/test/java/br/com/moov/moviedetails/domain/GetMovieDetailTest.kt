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
package br.com.moov.moviedetails.domain

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class GetMovieDetailTest {
    private val movies: List<MovieDetail> = listOf(
        MovieDetail(
            id = 123,
            title = "Lord of the Rings"
        )
    )
    private val getMovieDetail = GetMovieDetail(TestMovieDetailRepository(movies))

    @Test
    fun getMovieDetail_exists_returnsMovieDetail() = runBlocking {
        val actual = getMovieDetail(123)

        assertEquals(MovieDetail(123, "Lord of the Rings"), actual)
    }

    @Test
    fun getMovieDetail_notFound_returnsNull() = runBlocking {
        val actual = getMovieDetail(456)

        assertNull(actual)
    }
}

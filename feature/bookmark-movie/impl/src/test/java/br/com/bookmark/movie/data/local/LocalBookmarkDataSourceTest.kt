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
package br.com.bookmark.movie.data.local

import br.com.bookmark.movie.data.DatabaseError
import br.com.bookmark.movie.data.local.entity.MovieBookmark
import br.com.bookmark.movie.testdoubles.TestMovieBookmarksDao
import br.com.moov.core.result.Result
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class LocalBookmarkDataSourceTest {

    private val dbMovies = listOf(
        MovieBookmark(456),
    )
    private val movieBookmarksDao = TestMovieBookmarksDao(dbMovies)
    private val dataSource = LocalBookmarkDataSource(movieBookmarksDao)

    @Test
    fun bookmarkMovie_insertsOnDao() = runBlocking {
        dataSource.bookmarkMovie(movieId = 123)

        val expected = listOf(MovieBookmark(456), MovieBookmark(123))
        assertEquals(expected, movieBookmarksDao.getAll())
    }

    @Test
    fun bookmarkMovie_onError_returnsError() = runBlocking {
        val actual = dataSource.bookmarkMovie(movieId = 456)

        val expected = Result.Err(DatabaseError("Movie already on database"))
        assertEquals(expected, actual)
    }

    @Test
    fun unbookmarkMovie_removesFromDao() = runBlocking {
        dataSource.unBookmarkMovie(456)

        assertEquals(emptyList<MovieBookmark>(), movieBookmarksDao.getAll())
    }

    @Test
    fun unbookmarkMovie_onError_returnsError() = runBlocking {
        val actual = dataSource.unBookmarkMovie(123)

        val expected = Result.Err(DatabaseError("Movie not on database"))
        assertEquals(expected, actual)
    }
}

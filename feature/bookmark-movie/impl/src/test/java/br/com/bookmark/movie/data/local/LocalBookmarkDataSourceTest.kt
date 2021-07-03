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

import br.com.bookmark.movie.data.local.entity.MovieBookmark
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class LocalBookmarkDataSourceTest {

    @Test
    fun bookmarkMovie_insertsOnDao() = runBlocking {
        val movieBookmarksDao = TestMovieBookmarksDao()
        val dataSource = LocalBookmarkDataSource(movieBookmarksDao)

        dataSource.bookmarkMovie(movieId = 123)

        assertEquals(listOf(MovieBookmark(123)), movieBookmarksDao.getAll())
    }

    @Test
    fun unbookmarkMovie_removesFromDao() = runBlocking {
        val movieBookmarksDao = TestMovieBookmarksDao(listOf(MovieBookmark(123)))
        val dataSource = LocalBookmarkDataSource(movieBookmarksDao)

        dataSource.unBookmarkMovie(123)

        assertEquals(emptyList<MovieBookmark>(), movieBookmarksDao.getAll())
    }
}

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
package br.com.bookmark.movie.data

import br.com.bookmark.movie.testdoubles.TestBookmarkDataSource
import br.com.moov.bookmark.movie.BookmarkError
import br.com.moov.bookmark.movie.UnbookmarkError
import br.com.moov.core.result.Result
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class DefaultBookmarkRepositoryTest {

    private val bookmarkDataSource = TestBookmarkDataSource(listOf(123, 789))
    private val bookmarkRepository = DefaultBookmarkRepository(bookmarkDataSource)

    @Test
    fun bookmarkMovie_insertsOnDataSource() = runBlocking {
        bookmarkRepository.bookmarkMovie(456)

        assertEquals(listOf(123, 789, 456), bookmarkDataSource.getBookmarks())
    }

    @Test
    fun bookmarkMovie_fails_returnsError() = runBlocking {
        val actual = bookmarkRepository.bookmarkMovie(123)

        assertEquals(Result.Err(BookmarkError), actual)
    }

    @Test
    fun unBookmarkMovie_removesFromDataSource() = runBlocking {
        bookmarkRepository.unBookmarkMovie(123)

        assertEquals(listOf(789), bookmarkDataSource.getBookmarks())
    }

    @Test
    fun unBookmarkMovie_fails_returnsError() = runBlocking {
        val actual = bookmarkRepository.unBookmarkMovie(456)

        assertEquals(Result.Err(UnbookmarkError), actual)
    }
}

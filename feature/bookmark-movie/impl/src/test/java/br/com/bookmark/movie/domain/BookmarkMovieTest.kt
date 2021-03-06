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
package br.com.bookmark.movie.domain

import br.com.bookmark.movie.testdoubles.TestBookmarkRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class BookmarkMovieTest {

    private val repository = TestBookmarkRepository(listOf(123, 456))

    private val bookmark = BookmarkMovie(repository)

    @Test
    fun invoke_addOnRepository() = runBlocking {
        bookmark(789)

        assertEquals(listOf(123, 456, 789), repository.getBookmarks())
    }

    @Test
    fun invoke_existing_doesNothing() = runBlocking {
        bookmark(123)

        assertEquals(listOf(123, 456), repository.getBookmarks())
    }
}

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
package br.com.bookmark.movie.testdoubles

import br.com.bookmark.movie.data.BookmarkDataSource
import br.com.bookmark.movie.data.DatabaseError
import br.com.moov.core.result.Result

class TestBookmarkDataSource(
    initialBookmarks: List<Int> = emptyList()
) : BookmarkDataSource {

    private val bookmarks: MutableList<Int> = initialBookmarks.toMutableList()

    override suspend fun bookmarkMovie(movieId: Int): Result<Unit, DatabaseError> {
        return if (!bookmarks.contains(movieId)) {
            bookmarks.add(movieId)
            Result.Ok(Unit)
        } else {
            Result.Err(DatabaseError("Movie already bookmarked"))
        }
    }

    override suspend fun unBookmarkMovie(movieId: Int): Result<Unit, DatabaseError> {
        return if (bookmarks.contains(movieId)) {
            bookmarks.removeIf { it == movieId }
            Result.Ok(Unit)
        } else {
            Result.Err(DatabaseError("Movie not bookmarked"))
        }
    }

    fun getBookmarks(): List<Int> = bookmarks.toList()
}

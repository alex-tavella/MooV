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

class TestBookmarkDataSource(
    initialBookmarks: List<Int> = emptyList()
) : BookmarkDataSource {

    private val bookmarks: MutableList<Int> = initialBookmarks.toMutableList()

    override suspend fun bookmarkMovie(movieId: Int) {
        bookmarks.add(movieId)
    }

    override suspend fun unBookmarkMovie(movieId: Int) {
        bookmarks.removeIf { it == movieId }
    }

    fun getBookmarks(): List<Int> = bookmarks.toList()
}

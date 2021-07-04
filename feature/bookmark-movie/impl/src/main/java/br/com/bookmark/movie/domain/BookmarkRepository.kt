/*
 * Copyright 2020 Alex Almeida Tavella
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

import br.com.moov.bookmark.movie.BookmarkError
import br.com.moov.bookmark.movie.UnbookmarkError
import br.com.moov.core.result.Result

interface BookmarkRepository {
    suspend fun bookmarkMovie(movieId: Int): Result<Unit, BookmarkError>
    suspend fun unBookmarkMovie(movieId: Int): Result<Unit, UnbookmarkError>
}

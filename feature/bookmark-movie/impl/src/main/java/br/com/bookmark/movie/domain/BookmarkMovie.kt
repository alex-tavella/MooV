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
import br.com.moov.bookmark.movie.BookmarkMovieUseCase
import br.com.moov.core.di.AppScope
import br.com.moov.core.result.Result
import com.squareup.anvil.annotations.ContributesBinding
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class BookmarkMovie @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) : BookmarkMovieUseCase {
    override suspend fun invoke(movieId: Int): Result<Unit, BookmarkError> {
        return bookmarkRepository.bookmarkMovie(movieId)
    }
}

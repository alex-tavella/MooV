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
package br.com.bookmark.movie

import android.content.Context
import br.com.bookmark.movie.data.BookmarkDataSource
import br.com.bookmark.movie.data.DefaultBookmarkRepository
import br.com.bookmark.movie.data.local.BookmarksDatabase
import br.com.bookmark.movie.data.local.LocalBookmarkDataSource
import br.com.bookmark.movie.data.local.dao.MovieBookmarksDao
import br.com.bookmark.movie.domain.BookmarkMovie
import br.com.bookmark.movie.domain.BookmarkRepository
import br.com.bookmark.movie.domain.UnBookmarkMovie
import br.com.moov.bookmark.movie.BookmarkMovieUseCase
import br.com.moov.bookmark.movie.UnBookmarkMovieUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module(includes = [BookmarkInternalModule::class])
@InstallIn(ApplicationComponent::class)
object BookmarkExposedModule

@Module
@InstallIn(ApplicationComponent::class)
internal interface BookmarkInternalModule {

    @[Binds Reusable]
    fun bindsBookmarkDataSource(bookmarkDataSource: LocalBookmarkDataSource): BookmarkDataSource

    @[Binds Reusable]
    fun bindsBookmarkRepository(bookmarkRepository: DefaultBookmarkRepository): BookmarkRepository

    @[Binds Reusable]
    fun bindsBookmarkUseCase(bookmarkMovie: BookmarkMovie): BookmarkMovieUseCase

    @[Binds Reusable]
    fun bindsUnBookmarkUseCase(bookmarkMovie: UnBookmarkMovie): UnBookmarkMovieUseCase

    companion object {
        @[Provides Reusable]
        fun providesDatabase(context: Context): BookmarksDatabase {
            return BookmarksDatabase.create(context)
        }

        @[Provides Reusable]
        fun providesMovieBookmarksDao(database: BookmarksDatabase): MovieBookmarksDao {
            return database.movieBookmarksDao()
        }
    }
}

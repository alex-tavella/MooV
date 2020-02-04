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
package br.com.moov.data.movie.bookmark

import br.com.moov.data.movie.bookmark.database.MooVDatabase
import br.com.moov.data.movie.bookmark.database.entity.MovieEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val database: MooVDatabase) : BookmarkDataSource {
    override suspend fun getBookmarks(): List<MovieEntity> {
        return database.moviesDao().getAll()
    }

    override suspend fun getBookmarks(ids: IntArray): List<MovieEntity> {
        return database.moviesDao().loadAllByIds(ids)
    }

    override suspend fun isMovieBookmarked(movieId: Int): Boolean {
        return database.moviesDao().get(movieId) != null
    }

    override suspend fun bookmarkMovie(movie: MovieEntity) {
        database.moviesDao().insert(movie)
    }

    override suspend fun unbookmarkMovie(movieId: Int) {
        database.moviesDao().delete(movieId)
    }
}

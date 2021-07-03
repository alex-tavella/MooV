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

import br.com.bookmark.movie.data.local.dao.MovieBookmarksDao
import br.com.bookmark.movie.data.local.entity.MovieBookmark

class TestMovieBookmarksDao(
    initialMovies: List<MovieBookmark> = emptyList()
) : MovieBookmarksDao {

    private val movies = initialMovies.toMutableList()

    override suspend fun getAll(): List<MovieBookmark> {
        return movies.toList()
    }

    override suspend fun get(movieId: Int): MovieBookmark? {
        return movies.find { it.id == movieId }
    }

    override suspend fun loadAllByIds(movieIds: IntArray): List<MovieBookmark> {
        return movies.filter { movieIds.contains(it.id) }
    }

    override suspend fun insert(movie: MovieBookmark): Long {
        movies.add(movie)
        return movies.size.toLong()
    }

    override suspend fun delete(movieId: Int): Int {
        movies.removeIf { it.id == movieId }
        return movies.size
    }
}

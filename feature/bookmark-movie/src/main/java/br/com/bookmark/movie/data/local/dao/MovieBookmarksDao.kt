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
package br.com.bookmark.movie.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.bookmark.movie.data.local.entity.MovieBookmark

@Dao
internal interface MovieBookmarksDao {

    @Query("SELECT * FROM movie_bookmark")
    suspend fun getAll(): List<MovieBookmark>

    @Query("SELECT * FROM movie_bookmark WHERE id == :movieId")
    suspend fun get(movieId: Int): MovieBookmark?

    @Query("SELECT * FROM movie_bookmark WHERE id IN (:movieIds)")
    suspend fun loadAllByIds(movieIds: IntArray): List<MovieBookmark>

    @Insert
    suspend fun insert(movie: MovieBookmark): Long

    @Query("DELETE FROM movie_bookmark WHERE id = :movieId")
    suspend fun delete(movieId: Int): Int
}

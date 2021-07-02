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
package br.com.bookmark.movie.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.bookmark.movie.data.local.dao.MovieBookmarksDao
import br.com.bookmark.movie.data.local.entity.MovieBookmark

@Database(
    version = BookmarksDatabase.DB_VERSION,
    entities = [MovieBookmark::class]
)
abstract class BookmarksDatabase : RoomDatabase() {

    abstract fun movieBookmarksDao(): MovieBookmarksDao

    companion object {

        private const val DB_NAME = "bookmarks"
        const val DB_VERSION = 1

        fun create(context: Context): BookmarksDatabase {
            return Room.databaseBuilder(context, BookmarksDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}

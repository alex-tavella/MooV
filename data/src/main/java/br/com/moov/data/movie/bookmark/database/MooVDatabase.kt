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
package br.com.moov.data.movie.bookmark.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.moov.data.movie.bookmark.database.dao.MoviesDao
import br.com.moov.data.movie.bookmark.database.entity.MovieEntity

@Database(
    version = MooVDatabase.DB_VERSION,
    entities = [MovieEntity::class]
)
abstract class MooVDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

    companion object {

        private const val DB_NAME = "moov"
        const val DB_VERSION = 1

        fun create(context: Context): MooVDatabase {
            return Room.databaseBuilder(context, MooVDatabase::class.java, DB_NAME)
                .build()
        }
    }
}

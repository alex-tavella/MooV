/*
 *     Copyright 2017 Alex Almeida Tavella
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package br.com.alex.moov.domain.entity

import br.com.alex.moov.domain.AppDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.kotlinextensions.modelAdapter
import com.raizlabs.android.dbflow.structure.Model

@Table(database = AppDatabase::class)
data class Movie(
    @PrimaryKey var id: Int = 0,
    @Column var posterPath: String = "",
    @Column var overview: String = "",
    @Column var releaseDate: String = "",
    @Column var title: String = "",
    @Column var backdropPath: String = "",
    @Column var popularity: Float = 0f,
    @Column var voteAverage: Float = 0f) : Model {

  override fun insert() = modelAdapter<Movie>().insert(this)

  override fun save() = modelAdapter<Movie>().save(this)

  override fun update() = modelAdapter<Movie>().update(this)

  override fun exists() = modelAdapter<Movie>().exists(this)

  override fun delete() = modelAdapter<Movie>().delete(this)

  override fun load() = modelAdapter<Movie>().load(this)

}
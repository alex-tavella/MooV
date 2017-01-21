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
data class TvShow (
    @PrimaryKey var id: Int = 0,
    @Column var posterPath: String = "",
    @Column var popularity: Float = 0f,
    @Column var backdropPath: String = "",
    @Column var voteAverage: Float = 0f,
    @Column var overview: String = "",
    @Column var name: String = "",
    @Column var voteCount: Int = 0) : Model {

  override fun insert() = modelAdapter<TvShow>().insert(this)

  override fun save() = modelAdapter<TvShow>().save(this)

  override fun update() = modelAdapter<TvShow>().update(this)

  override fun exists() = modelAdapter<TvShow>().exists(this)

  override fun delete() = modelAdapter<TvShow>().delete(this)

  override fun load() = modelAdapter<TvShow>().load(this)

}
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

import android.os.Parcel
import android.os.Parcelable
import br.com.alex.moov.domain.repository.AppDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.kotlinextensions.modelAdapter
import com.raizlabs.android.dbflow.structure.Model

@Table(database = AppDatabase::class)
data class TvShow(
    @PrimaryKey var id: Int = 0,
    @Column var posterUrl: String = "",
    @Column var popularity: Float = 0f,
    @Column var backdropUrl: String = "",
    @Column var voteAverage: Float = 0f,
    @Column var overview: String = "",
    @Column var name: String = "",
    @Column var voteCount: Int = 0) : Model, Parcelable {
  override fun insert() = modelAdapter<TvShow>().insert(this)

  override fun save() = modelAdapter<TvShow>().save(this)

  override fun update() = modelAdapter<TvShow>().update(this)

  override fun exists() = modelAdapter<TvShow>().exists(this)

  override fun delete() = modelAdapter<TvShow>().delete(this)

  override fun load() = modelAdapter<TvShow>().load(this)

  companion object {
    @JvmField val CREATOR: Parcelable.Creator<TvShow> = object : Parcelable.Creator<TvShow> {
      override fun createFromParcel(source: Parcel): TvShow = TvShow(source)
      override fun newArray(size: Int): Array<TvShow?> = arrayOfNulls(size)
    }
  }

  constructor(
      source: Parcel) : this(source.readInt(), source.readString(), source.readFloat(),
      source.readString(), source.readFloat(), source.readString(), source.readString(),
      source.readInt())

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel?, flags: Int) {
    dest?.writeInt(id)
    dest?.writeString(posterUrl)
    dest?.writeFloat(popularity)
    dest?.writeString(backdropUrl)
    dest?.writeFloat(voteAverage)
    dest?.writeString(overview)
    dest?.writeString(name)
    dest?.writeInt(voteCount)
  }
}
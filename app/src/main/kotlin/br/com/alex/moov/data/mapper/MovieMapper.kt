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

package br.com.alex.moov.data.mapper

import br.com.alex.moov.data.tmdb.model.TMDBMovie
import br.com.alex.moov.domain.entity.Movie

class MovieMapper : Mapper<TMDBMovie, Movie> {
  override fun map(source: TMDBMovie) = Movie(source.id, source.poster_path, source.overview,
      source.release_date, source.original_title, source.backdrop_path,
      source.popularity, source.vote_average)
}
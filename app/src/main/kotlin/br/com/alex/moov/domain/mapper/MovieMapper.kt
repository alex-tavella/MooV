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

package br.com.alex.moov.domain.mapper

import br.com.alex.moov.api.tmdb.model.ImageConfigurations
import br.com.alex.moov.api.tmdb.model.TMDBMovie
import br.com.alex.moov.domain.entity.Movie
import javax.inject.Inject

class MovieMapper {

  @Inject constructor()

  fun map(source: TMDBMovie, imageConfigs: ImageConfigurations) =
      Movie(source.id,
          imageConfigs.base_url + imageConfigs.poster_sizes.first() + source.poster_path,
          source.overview,
          source.release_date, source.original_title,
          imageConfigs.base_url + imageConfigs.backdrop_sizes.first() + source.backdrop_path,
          source.popularity, source.vote_average)
}
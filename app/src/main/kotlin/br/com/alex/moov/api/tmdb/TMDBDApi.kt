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

package br.com.alex.moov.api.tmdb

import br.com.alex.moov.api.tmdb.model.ConfigurationsResponse
import br.com.alex.moov.api.tmdb.model.DiscoverResponse
import br.com.alex.moov.api.tmdb.model.TMDBMovie
import br.com.alex.moov.api.tmdb.model.TMDBTvShow
import retrofit2.http.GET
import rx.Single

interface TMDBDApi {

  @GET("/3/discover/movie")
  fun discoverMovies(): Single<DiscoverResponse<TMDBMovie>>

  @GET("/3/discover/tv")
  fun discoverTvShows(): Single<DiscoverResponse<TMDBTvShow>>

  @GET("/3/configuration")
  fun getConfiguration(): Single<ConfigurationsResponse>
}
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

package br.com.alex.moov.api.tmdb.model

data class TMDBMovie(val id: Int, val poster_path: String, val adult: Boolean, val overview: String,
    val release_date: String, val genre_ids: List<String>, val original_title: String,
    val original_language: String, val title: String, val backdrop_path: String,
    val popularity: Float, val vote_count: Int, val video: Boolean, val vote_average: Float)
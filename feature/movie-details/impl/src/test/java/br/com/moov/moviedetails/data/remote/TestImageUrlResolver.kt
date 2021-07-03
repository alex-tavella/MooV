/*
 * Copyright 2021 Alex Almeida Tavella
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
package br.com.moov.moviedetails.data.remote

import br.com.moov.core.ImageUrlResolver

class TestImageUrlResolver(
    private val baseUrl: String = "https://cdn.tmdb.com/images"
) : ImageUrlResolver {
    override suspend fun getPosterUrl(posterPath: String): String {
        return "$baseUrl/poster/$posterPath"
    }

    override suspend fun getBackdropUrl(backdropPath: String): String {
        return "$baseUrl/backdrop/$backdropPath"
    }
}

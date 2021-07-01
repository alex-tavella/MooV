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
package br.com.core.tmdb.image

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.http.GET

internal interface ImageConfigurationApi {
    @GET("/3/configuration")
    suspend fun getConfiguration(): ConfigurationsResponse
}

@JsonClass(generateAdapter = true)
internal data class ConfigurationsResponse(val images: ImageConfigurations)

@JsonClass(generateAdapter = true)
internal data class ImageConfigurations(
    @Json(name = "base_url") val baseUrl: String,
    @Json(name = "backdrop_sizes") val backdropSizes: List<String> = emptyList(),
    @Json(name = "poster_sizes") val posterSizes: List<String> = emptyList()
)

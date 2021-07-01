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

import javax.inject.Inject

internal class AverageImageConfigurationProvider @Inject constructor(
    private val imageConfigurationApi: ImageConfigurationApi
) : ImageConfigurationProvider {

    private lateinit var imageConfigs: ConfigurationsResponse

    private val baseUrl: String
        get() = imageConfigs.images.baseUrl

    override suspend fun getBasePosterUrl(): String {
        fetchConfigsIfNeeded()
        return baseUrl + selectAverageOption(imageConfigs.images.posterSizes)
    }

    override suspend fun getBaseBackdropUrl(): String {
        fetchConfigsIfNeeded()
        return baseUrl + selectAverageOption(imageConfigs.images.backdropSizes)
    }

    private suspend fun fetchConfigsIfNeeded() {
        if (!::imageConfigs.isInitialized) {
            imageConfigs = imageConfigurationApi.getConfiguration()
        }
    }

    private fun selectAverageOption(options: List<String>): String {
        return options[(options.size / 2)]
    }
}

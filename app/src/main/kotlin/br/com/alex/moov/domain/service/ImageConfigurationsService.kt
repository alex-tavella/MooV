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

package br.com.alex.moov.domain.service

import br.com.alex.moov.api.tmdb.TMDBDApi
import br.com.alex.moov.api.tmdb.model.ImageConfigurations
import br.com.alex.moov.domain.repository.Repository
import rx.Single
import timber.log.Timber

class ImageConfigurationsService(val imageConfigurationsRepository: Repository<ImageConfigurations>,
    val tmdbdApi: TMDBDApi) {

  fun retrieveImageConfigurations(): Single<ImageConfigurations> {
    return imageConfigurationsRepository.load()
        .onErrorResumeNext {
          tmdbdApi.getConfiguration()
              .map { it.images }
              .doOnSuccess { imageConfigurationsRepository.save(it) }
        }
        .doOnSuccess { Timber.d("$it") }
        .doOnError { Timber.e("$it") }
  }
}
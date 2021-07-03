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
package br.com.moov.moviedetails.data

import br.com.moov.moviedetails.data.remote.TmdbMovieMapper
import br.com.moov.moviedetails.di.MovieDetailScope
import br.com.moov.moviedetails.domain.MovieDetail
import br.com.moov.moviedetails.domain.MovieDetailRepository
import com.squareup.anvil.annotations.ContributesBinding
import javax.inject.Inject

@ContributesBinding(MovieDetailScope::class)
class DefaultMovieDetailRepository @Inject constructor(
    private val movieDetailDataSource: MovieDetailDataSource,
) : MovieDetailRepository {
    override suspend fun getMovieDetail(movieId: Int): MovieDetail? {
        return movieDetailDataSource.getMovieDetail(movieId)
    }
}

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
package br.com.moov.moviedetails.testdoubles

import br.com.moov.core.result.Result
import br.com.moov.moviedetails.data.MovieDetailApiError
import br.com.moov.moviedetails.data.MovieDetailDataSource
import br.com.moov.moviedetails.domain.MovieDetail

class TestMovieDetailDataSource(
    private val moviesDetails: List<MovieDetail> = emptyList()
) : MovieDetailDataSource {
    override suspend fun getMovieDetail(movieId: Int): Result<MovieDetail, MovieDetailApiError> {
        return moviesDetails.find { it.id == movieId }?.let {
            Result.Ok(it)
        } ?: Result.Err(MovieDetailApiError.Other("Not Found"))
    }
}
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

import br.com.moov.moviedetails.data.remote.TMDBMovieDetailApi
import br.com.moov.moviedetails.data.remote.TmdbMovieDetail
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

class TestTMDBMovieDetailApi(
    private val movies: List<TmdbMovieDetail> = emptyList()
) : TMDBMovieDetailApi {

    override suspend fun getMovie(movieId: Int): TmdbMovieDetail {
        return movies.find { it.id == movieId }
            ?: throw HttpException(
                Response.error<TmdbMovieDetail>(
                    404,
                    ResponseBody.create(null, "Not found")
                )
            )
    }
}
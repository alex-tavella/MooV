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
package br.com.moov.movies.data.remote

import br.com.moov.core.result.Result
import br.com.moov.movies.data.MovieDataSource
import br.com.moov.movies.data.MoviesRemoteDataSourceError
import br.com.moov.movies.di.MoviesScope
import br.com.moov.movies.domain.Movie
import com.squareup.anvil.annotations.ContributesBinding
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val SORT_ORDER_POPULARITY = "popularity.desc"
private const val THRESHOLD_VOTE_COUNT = 100

@ContributesBinding(MoviesScope::class)
class TMDBMovieDataSource @Inject constructor(
    private val tmdbdApi: TmdbMoviesApi,
    private val mapper: MoviesResponseMapper
) : MovieDataSource {
    override suspend fun getMovies(page: Int): Result<List<Movie>, MoviesRemoteDataSourceError> =
        try {
            Result.Ok(
                tmdbdApi.discoverMovies(page, SORT_ORDER_POPULARITY, THRESHOLD_VOTE_COUNT)
                    .results.map { mapper.map(it) }
            )
        } catch (exception: HttpException) {
            Result.Err(MoviesRemoteDataSourceError.Http(exception.code()))
        } catch (exception: IOException) {
            Result.Err(MoviesRemoteDataSourceError.Other(exception.javaClass.simpleName))
        }
}

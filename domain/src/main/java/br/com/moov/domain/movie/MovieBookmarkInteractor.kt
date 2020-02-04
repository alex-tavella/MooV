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
package br.com.moov.domain.movie

import javax.inject.Inject

interface MovieBookmarkInteractor {
    suspend fun getBookmarkedMovies(movies: List<Movie>): List<Movie>
    suspend fun bookmarkMovie(movie: Movie)
    suspend fun bookmarkMovie(movie: MovieDetail)
    suspend fun unbookmarkMovie(movieId: Int)
}

class MovieBookmarkInteractorImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : MovieBookmarkInteractor {

    override suspend fun getBookmarkedMovies(movies: List<Movie>): List<Movie> {
        return movieRepository.getBookmarkedMovies(movies)
    }

    override suspend fun bookmarkMovie(movie: Movie) {
        movieRepository.bookmark(movie)
    }

    override suspend fun bookmarkMovie(movie: MovieDetail) {
        movieRepository.bookmark(movie)
    }

    override suspend fun unbookmarkMovie(movieId: Int) {
        movieRepository.unbookmark(movieId)
    }
}

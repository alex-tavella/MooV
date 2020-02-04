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
package br.com.moov.data.movie

import br.com.moov.data.movie.bookmark.BookmarkDataSource
import br.com.moov.domain.movie.Movie
import br.com.moov.domain.movie.MovieDetail
import br.com.moov.domain.movie.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val tmdbMovieDataSource: MovieDataSource,
    private val bookmarkDataSource: BookmarkDataSource,
    private val movieMapper: MovieMapper
) : MovieRepository {

    override suspend fun getPopularMovies(page: Int): List<Movie> {
        val tmdbMovies = tmdbMovieDataSource.getMovies(
            page, SORT_ORDER_POPULARITY,
            THRESHOLD_VOTE_COUNT
        )
        val imageConfigs = tmdbMovieDataSource.getImageConfigs()

        val bookmarkedIds = bookmarkDataSource
            .getBookmarks(tmdbMovies.mapNotNull { it.id }.toIntArray())
            .map { it.id }

        return tmdbMovies
            .map {
                movieMapper.map(it, imageConfigs).copy(isBookmarked = bookmarkedIds.contains(it.id))
            }
    }

    override suspend fun getMovieDetail(movieId: Int): MovieDetail {
        val tmdbMovie = tmdbMovieDataSource.getMovieDetail(movieId)
        val imageConfigs = tmdbMovieDataSource.getImageConfigs()
        val movieBookmarked = bookmarkDataSource.isMovieBookmarked(movieId)
        return tmdbMovie.let {
            movieMapper.map(it, imageConfigs).copy(isBookmarked = movieBookmarked)
        }
    }

    override suspend fun getBookmarkedMovies(movies: List<Movie>): List<Movie> {
        val bookmarkedIds = bookmarkDataSource
            .getBookmarks(movies.mapNotNull { it.id }.toIntArray())
            .map { it.id }

        return movies.map { it.copy(isBookmarked = bookmarkedIds.contains(it.id)) }
    }

    override suspend fun bookmark(movie: Movie) {
        movieMapper.toEntity(movie)?.let { bookmarkDataSource.bookmarkMovie(it) }
    }

    override suspend fun bookmark(movieDetail: MovieDetail) {
        movieMapper.toEntity(movieDetail)?.let { bookmarkDataSource.bookmarkMovie(it) }
    }

    override suspend fun unbookmark(movieId: Int) {
        bookmarkDataSource.unbookmarkMovie(movieId)
    }

    companion object {
        const val SORT_ORDER_POPULARITY = "popularity.desc"
        const val THRESHOLD_VOTE_COUNT = 100
    }
}

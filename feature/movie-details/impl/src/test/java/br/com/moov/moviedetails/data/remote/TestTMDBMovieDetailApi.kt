package br.com.moov.moviedetails.data.remote

import java.io.IOException

class TestTMDBMovieDetailApi(
    private val movies: List<TmdbMovieDetail> = emptyList()
) : TMDBMovieDetailApi {

    override suspend fun getMovie(movieId: Int): TmdbMovieDetail {
        return movies.find { it.id == movieId } ?: throw IOException("404: Not found")
    }
}

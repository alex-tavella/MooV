package br.com.moov.data.movie

import br.com.moov.data.common.await
import br.com.moov.data.movie.tmdb.ImageConfigurations
import br.com.moov.data.movie.tmdb.TMDBDApi
import br.com.moov.data.movie.tmdb.TMDBMovie
import br.com.moov.data.movie.tmdb.TMDBMovieDetail
import javax.inject.Inject

interface MovieDataSource {
    suspend fun getMovies(page: Int, sortBy: String, voteCount: Int): List<TMDBMovie>
    suspend fun getMovieDetail(id: Int): TMDBMovieDetail
    suspend fun getImageConfigs(): ImageConfigurations
}

class TMDBMovieDataSource @Inject constructor(private val tmdbdApi: TMDBDApi) : MovieDataSource {
    override suspend fun getMovies(page: Int, sortBy: String, voteCount: Int): List<TMDBMovie> =
        tmdbdApi.discoverMovies(page, sortBy, voteCount).await().results

    override suspend fun getMovieDetail(id: Int): TMDBMovieDetail = tmdbdApi.getMovie(id).await()

    override suspend fun getImageConfigs() = tmdbdApi.getConfiguration().await().images
}

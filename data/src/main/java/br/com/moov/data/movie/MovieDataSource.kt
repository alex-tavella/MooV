package br.com.moov.data.movie

import br.com.moov.data.common.await
import br.com.moov.data.movie.tmdb.TMDBDApi
import br.com.moov.data.movie.tmdb.TMDBMovie

interface MovieDataSource {
  suspend fun getMovies(page: Int, sortBy: String, voteCount: Int): List<TMDBMovie>
  suspend fun getMovie(id: Int): TMDBMovie
}

class TMDBMovieDataSource(private val tmdbdApi: TMDBDApi) : MovieDataSource {
  override suspend fun getMovies(page: Int, sortBy: String, voteCount: Int): List<TMDBMovie> =
      tmdbdApi.discoverMovies(page, sortBy, voteCount).await().results

  override suspend fun getMovie(id: Int): TMDBMovie = tmdbdApi.getMovie(id).await()
}
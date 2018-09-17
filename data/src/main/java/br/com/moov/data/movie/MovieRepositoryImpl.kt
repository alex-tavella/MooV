package br.com.moov.data.movie

import br.com.moov.domain.movie.Movie
import br.com.moov.domain.movie.MovieRepository

class MovieRepositoryImpl(
    private val tmdbMovieDataSource: TMDBMovieDataSource,
    private val movieMapper: MovieEntityMapper) : MovieRepository {
  override suspend fun getMovies(page: Int): List<Movie> = tmdbMovieDataSource.getMovies(page,
      "vote_average.desc", 100)
      .map { movieMapper.map(it) }
}
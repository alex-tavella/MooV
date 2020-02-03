package br.com.moov.domain.movie

import javax.inject.Inject

interface MovieDetailInteractor {
  suspend fun getMovieDetail(movieId: Int): MovieDetail
}

class MovieDetailInteractorImpl @Inject constructor(
    private val movieRepository: MovieRepository) : MovieDetailInteractor {
  override suspend fun getMovieDetail(movieId: Int): MovieDetail {
    return movieRepository.getMovieDetail(movieId)
  }
}

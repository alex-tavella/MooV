package br.com.moov.domain.movie

interface MovieDetailInteractor {
  suspend fun getMovieDetail(movieId: Int): MovieDetail
}

class MovieDetailInteractorImpl(
    private val movieRepository: MovieRepository) : MovieDetailInteractor {
  override suspend fun getMovieDetail(movieId: Int): MovieDetail {
    return movieRepository.getMovieDetail(movieId)
  }
}
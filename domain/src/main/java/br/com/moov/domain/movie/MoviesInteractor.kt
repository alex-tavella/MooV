package br.com.moov.domain.movie

interface MoviesInteractor {
  suspend fun getPopularMovies(page: Int = 1): List<Movie>
}

class MoviesInteractorImpl(private val movieRepository: MovieRepository): MoviesInteractor {
  override suspend fun getPopularMovies(page: Int): List<Movie> = movieRepository.getPopularMovies(page)
}
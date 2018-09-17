package br.com.moov.domain.movie

interface MovieInteractor {
  suspend fun getMovies(page: Int = 1): List<Movie>
}

class MovieInteractorImpl(private val movieRepository: MovieRepository): MovieInteractor {
  override suspend fun getMovies(page: Int): List<Movie> = movieRepository.getMovies(page)
}
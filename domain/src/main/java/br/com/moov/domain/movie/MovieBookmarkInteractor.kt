package br.com.moov.domain.movie

interface MovieBookmarkInteractor {
  suspend fun getBookmarkedMovies(movies: List<Movie>): List<Movie>
  suspend fun bookmarkMovie(movie: Movie)
  suspend fun bookmarkMovie(movie: MovieDetail)
  suspend fun unbookmarkMovie(movieId: Int)
}

class MovieBookmarkInteractorImpl(
    private val movieRepository: MovieRepository) : MovieBookmarkInteractor {

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